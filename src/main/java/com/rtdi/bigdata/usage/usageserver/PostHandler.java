package com.rtdi.bigdata.usage.usageserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.rtdi.bigdata.usage.usageserver.UsageStatistics.ConnectionEntry;
import com.rtdi.bigdata.usage.usageserver.UsageStatistics.ConsumerEntry;
import com.rtdi.bigdata.usage.usageserver.UsageStatistics.ConsumerInstanceEntry;
import com.rtdi.bigdata.usage.usageserver.UsageStatistics.ProducerEntry;
import com.rtdi.bigdata.usage.usageserver.UsageStatistics.ProducerInstanceEntry;

public class PostHandler implements RequestHandler<UsageStatistics, String> {
	
	private static Connection conn = null;

	@Override
	public String handleRequest(UsageStatistics data, Context context) {
		try {
			if (conn == null | (conn != null && !conn.isValid(10))) {
				conn = DriverManager.getConnection("jdbc:mysql://usagecollection.cw4ekzxmymyo.eu-central-1.rds.amazonaws.com/usageowner?user=usageowner&password=KSSF2eu2sFAWNww");
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			context.getLogger().log(e.getMessage());
			return "Connection creation failed with error " + e.getMessage();
		}
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into connector (connectorname, sourceip, starttime, endtime) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, data.getConnectorname());
			stmt.setString(2, data.getSourceip());
			if (data.getStarttime() == 0) {
				stmt.setNull(3, Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(3, new Timestamp(data.getStarttime()));
			}
			if (data.getEndtime() == 0) {
				stmt.setNull(4, Types.TIMESTAMP);
			} else {
				stmt.setTimestamp(4, new Timestamp(data.getEndtime()));
			}
			stmt.execute();
			ResultSet rs1 = stmt.getGeneratedKeys();
			int id;
			if (rs1.next()) {
				id = rs1.getInt(1);
			} else {
				throw new SQLException("Could not get the generated key when inserting into table \"connector\"");
			}
			stmt.close();
			
			if (data.getConnections() != null) {
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into producer (connectorid, connectionname, producername, instance, lastdata, pollcalls, rowsproduced, controllerstate) " + 
						"values (?,?,?,?,?,?,?,?)");
				PreparedStatement cstmt = conn.prepareStatement(
						"insert into consumer (connectorid, connectionname, consumername, instance, lastdata, fetchcalls, rowsfetched, controllerstate) " + 
						"values (?,?,?,?,?,?,?,?)");
				for (ConnectionEntry connection : data.getConnections()) {
					if (connection.getProducers() != null) {
						for (ProducerEntry producer : connection.getProducers()) {
							if (producer.getInstances() != null) {
								int counter = 0;
								for (ProducerInstanceEntry instance : producer.getInstances()) {
									pstmt.setInt(1, id);
									pstmt.setString(2, connection.getConnectionname());
									pstmt.setString(3, producer.getProducername());
									pstmt.setInt(4, counter++);
									if (instance.getLastdatatimestamp() == 0) {
										stmt.setNull(5, Types.TIMESTAMP);
									} else {
										pstmt.setTimestamp(5, new Timestamp(instance.getLastdatatimestamp()));
									}
									pstmt.setInt(6, instance.getPollcalls());
									pstmt.setLong(7, instance.getRowsproduced());
									pstmt.setString(8, instance.getState());
									pstmt.execute();
								}
							}
						}
					}
					if (connection.getConsumers() != null) {
						for (ConsumerEntry consumer : connection.getConsumers()) {
							if (consumer.getInstances() != null) {
								int counter = 0;
								for (ConsumerInstanceEntry instance : consumer.getInstances()) {
									cstmt.setInt(1, id);
									cstmt.setString(2, connection.getConnectionname());
									cstmt.setString(3, consumer.getConsumername());
									cstmt.setInt(4, counter++);
									if (instance.getLastdatatimestamp() == 0) {
										stmt.setNull(5, Types.TIMESTAMP);
									} else {
										cstmt.setTimestamp(5, new Timestamp(instance.getLastdatatimestamp()));
									}
									cstmt.setInt(6, instance.getFetchcalls());
									cstmt.setLong(7, instance.getRowsfetched());
									cstmt.setString(8, instance.getState());
									cstmt.execute();
								}
							}
						}
					}
				}
				pstmt.close();
				cstmt.close();
			}
			conn.commit();
		} catch (SQLException e) {
			context.getLogger().log(e.getMessage());
			return "saving data failed with error " + e.getMessage();
		}
		return "saved";
	}
}
