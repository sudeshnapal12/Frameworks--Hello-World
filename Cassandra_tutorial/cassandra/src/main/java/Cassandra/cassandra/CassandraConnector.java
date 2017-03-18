package Cassandra.cassandra;

import java.util.Optional;

import org.apache.log4j.PropertyConfigurator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraConnector {
	/** Cassandra Cluster. */
	private Cluster cluster;
	/** Cassandra Session. */
	private Session session;
	private Session newSession;

	public void connect(final String serverIP, final int port, String keyspace) {
		this.cluster = Cluster.builder().addContactPoint(serverIP).withPort(port).build();

		final Metadata metadata = cluster.getMetadata();
		System.out.println("Connected to cluster: " + metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			System.out.println("Datacenter:  " + host.getDatacenter() + " Host: " + host.getAddress() + " Rack: "
					+ host.getRack());
		}

		this.session = cluster.connect(keyspace);
	}

	public void read() {
		String cqlStatement = "SELECT * FROM emp";
		for (Row row : session.execute(cqlStatement)) {
			System.out.print("sud :: ");
			System.out.println(row.toString());
		}
	}

	public void createKeySpace(String serverIP) {
		String cqlStatement = "CREATE KEYSPACE myfirstcassandradb WITH "
				+ "replication = {'class':'SimpleStrategy','replication_factor':1}";
		session.execute(cqlStatement);

		// based on the above keyspace, we would change the cluster and session
		// as follows:
		Cluster cluster = Cluster.builder().addContactPoints(serverIP).build();
		newSession = cluster.connect("myfirstcassandradb");

		crud();
	}

	public void crud() {
		String cqlStatement = "CREATE TABLE users (" + " username varchar PRIMARY KEY," + " password varchar " + ");";

		newSession.execute(cqlStatement);

		String cqlStatementC = "INSERT INTO myfirstcassandradb.users (username, password) "
				+ "VALUES ('sud', 'password123')";

		String cqlStatementU = "UPDATE myfirstcassandradb.users " + "SET password = 'password569' "
				+ "WHERE username = 'sud';";

		String cqlStatementD = "DELETE FROM myfirstcassandradb.users " + "WHERE username = 'sud';";

		newSession.execute(cqlStatementC);
		newSession.execute(cqlStatementU);
		// newSession.execute(cqlStatementD);
	}

	public User queryUser(final String username) {
		final ResultSet userResults = session.execute(
				"SELECT * from myfirstcassandradb.users WHERE username = ? ", username);
		final Row userRow = userResults.one();

		final User user = userRow != null 
				? new User(userRow.getString("username"), userRow.getString("password"))
				: null;
		return user;
	}

	public Session getSession() {
		return this.session;
	}

	public void close() {
		cluster.close();
	}

	public static void main(final String[] args) {
		String log4jConfPath = "C:/Users/dexter/Documents/WORK/workspace/cassandra/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);

		// BasicConfigurator.configure();

		final CassandraConnector client = new CassandraConnector();
		final String ipAddress = args.length > 0 ? args[0] : "localhost";
		final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
		String keyspace = "dev";

		System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");

		client.connect(ipAddress, port, keyspace);
		client.read();
		
		client.createKeySpace(ipAddress);
		System.out.println(client.queryUser("sud").toString());
		
		client.close();
	}
}