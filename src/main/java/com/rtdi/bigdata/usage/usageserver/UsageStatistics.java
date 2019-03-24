package com.rtdi.bigdata.usage.usageserver;

import java.util.List;

public class UsageStatistics {

	private String connectorname;
	private List<ConnectionEntry> connections;
	private long starttime;
	private long endtime;
	private String sourceip;
	private String connectorjar;
	private String apijar;

	public UsageStatistics() {
	}

	public String getConnectorname() {
		return connectorname;
	}

	public void setConnectorname(String connectorname) {
		this.connectorname = connectorname;
	}

	public List<ConnectionEntry> getConnections() {
		return connections;
	}

	public void setConnections(List<ConnectionEntry> connections) {
		this.connections = connections;
	}

	public long getStarttime() {
		return starttime;
	}

	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public String getSourceip() {
		return sourceip;
	}

	public void setSourceip(String sourceip) {
		this.sourceip = sourceip;
	}

	public String getPipelineAPIjar() {
		return apijar;
	}

	public void setPipelineAPIjar(String apijar) {
		this.apijar = apijar;
	}
	
	public String getConnectorjar() {
		return connectorjar;
	}

	public void setConnectorjar(String connectorjar) {
		this.connectorjar = connectorjar;
	}

	public static class ConnectionEntry {

		private String connectionname;
		private List<ProducerEntry> producers;
		private List<ConsumerEntry> consumers;

		public ConnectionEntry() {
		}
		
		public String getConnectionname() {
			return connectionname;
		}

		public void setConnectionname(String connectionname) {
			this.connectionname = connectionname;
		}

		public List<ProducerEntry> getProducers() {
			return producers;
		}

		public void setProducers(List<ProducerEntry> producers) {
			this.producers = producers;
		}

		public List<ConsumerEntry> getConsumers() {
			return consumers;
		}

		public void setConsumers(List<ConsumerEntry> consumers) {
			this.consumers = consumers;
		}

	}
	
	public static class ProducerEntry {

		private String producername;
		private List<ProducerInstanceEntry> instances;

		public ProducerEntry() {
		}

		public String getProducername() {
			return producername;
		}

		public void setProducername(String producername) {
			this.producername = producername;
		}

		public List<ProducerInstanceEntry> getInstances() {
			return instances;
		}

		public void setInstances(List<ProducerInstanceEntry> instances) {
			this.instances = instances;
		}

	}
	
	public static class ProducerInstanceEntry {

		private Long lastdatatimestamp;
		private int pollcalls;
		private String state;
		private long rowsproduced;

		public ProducerInstanceEntry() {
		}

		public Long getLastdatatimestamp() {
			return lastdatatimestamp;
		}

		public void setLastdatatimestamp(Long lastdatatimestamp) {
			this.lastdatatimestamp = lastdatatimestamp;
		}

		public int getPollcalls() {
			return pollcalls;
		}

		public void setPollcalls(int pollcalls) {
			this.pollcalls = pollcalls;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public long getRowsproduced() {
			return rowsproduced;
		}

		public void setRowsproduced(long rowsproduced) {
			this.rowsproduced = rowsproduced;
		}

	}
	
	public static class ConsumerEntry {

		private String consumername;
		private List<ConsumerInstanceEntry> instances;

		public ConsumerEntry() {
		}

		public String getConsumername() {
			return consumername;
		}

		public void setConsumername(String consumername) {
			this.consumername = consumername;
		}

		public List<ConsumerInstanceEntry> getInstances() {
			return instances;
		}

		public void setInstances(List<ConsumerInstanceEntry> instances) {
			this.instances = instances;
		}

	}
	
	public static class ConsumerInstanceEntry {
		
		private Long lastdatatimestamp;
		private int fetchcalls;
		private long rowsfetched;
		private String state;

		public ConsumerInstanceEntry() {
		}

		public Long getLastdatatimestamp() {
			return lastdatatimestamp;
		}

		public void setLastdatatimestamp(Long lastdatatimestamp) {
			this.lastdatatimestamp = lastdatatimestamp;
		}

		public int getFetchcalls() {
			return fetchcalls;
		}

		public void setFetchcalls(int fetchcalls) {
			this.fetchcalls = fetchcalls;
		}

		public long getRowsfetched() {
			return rowsfetched;
		}

		public void setRowsfetched(long rowsfetched) {
			this.rowsfetched = rowsfetched;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
	
}
