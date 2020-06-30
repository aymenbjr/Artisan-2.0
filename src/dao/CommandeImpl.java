package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Commande;

public class CommandeImpl implements ICommande{

	@Override
	public void addCommande(Commande c) {
		Connection conn=DBconnect.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement("insert into commande(id_Client,id_Voyage,destination,type,date,duree) values (?,?,?,?,?,? )");
			st.setInt(1, c.getId_client());
			st.setInt(2, c.getId_Voyage());
			st.setString(3,  c.getDestination() );
			st.setString(4,  c.getType() );
			st.setString(5,  c.getDate() );
			st.setInt(6,  c.getDuree() );
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCommande(int id_c) {
		Connection conn=DBconnect.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement("delete from commande where id_commande=?");
			st.setInt(1,id_c);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Commande> ListCommande() {
		List<Commande> cmd = new ArrayList();
		Connection conn=DBconnect.getConnection();
		
		try {
			PreparedStatement st=conn.prepareStatement("select * from commande");
			ResultSet rs=st.executeQuery();
			while(rs.next()){
				Commande c=new Commande();;
				c.setId_commande(rs.getInt("id_commande"));
				c.setId_client(rs.getInt("id_client"));
				c.setId_Voyage(rs.getInt("id_Voyage"));
				c.setDestination(rs.getString("destination"));
				c.setType(rs.getString("type"));
				c.setDate(rs.getString("date"));
				c.setDuree(rs.getInt("duree"));
				cmd.add(c);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cmd;
	}
	
	

}
