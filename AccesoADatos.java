package pla4CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class AccesoADatos {
	
	String connexio = "jdbc:mysql://localhost:3306/";
	String BBDD = "gestor";
	String usuari = "root";
	String contrasenya = "Cifo2019";
	
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	Connection con;
	

	//-------------------------------------------------------------------------------------------------
	// Crear la connexió amb la base de dades
	//-------------------------------------------------------------------------------------------------
	public void Connectar_BBDD() {

	try {
		//System.out.println("Connectant amb la base de dades");	
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(connexio + BBDD, usuari, contrasenya);
			
		} 
	catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	//-------------------------------------------------------------------------------------------------
	// Retornar un registre
	//-------------------------------------------------------------------------------------------------	
		public boolean existeix_registre(String nom_taula, String id_registre, String clau_principal) 
			{
			try {
				String wSentenciaSQL = "select * from " + nom_taula + " where " + clau_principal + " = '" + id_registre + "'";
		//		System.out.println("Llençant consulta " + wSentenciaSQL);
				pstmt = con.prepareStatement(wSentenciaSQL);
		//		pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) 
					{
					return true;
					} 
				else 
					{
					return false;
					}
				} 
			catch (Exception e) 
				{
				System.out.println(e);
				return false;
				}
			}
	//-------------------------------------------------------------------------------------------------
	// Retornar un registre
	//-------------------------------------------------------------------------------------------------	
	public ResultSet consultar_registre(String nom_taula, String id_registre,String clau_principal) 
		{
		try {
			String wSentenciaSQL = "select * from " + nom_taula + " where " + clau_principal + " = '" + id_registre + "'";
	//		System.out.println("Llençant consulta " + wSentenciaSQL);
			pstmt = con.prepareStatement(wSentenciaSQL);
	//		pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) 
				{
				return rs;
				} 
			else 
				{
				return null;
				}
			} 
		catch (Exception e) 
			{
			System.out.println(e);
			return null;
			}
		}
	//-------------------------------------------------------------------------------------------------
	// Retornar tots els registres d'una taula
	//-------------------------------------------------------------------------------------------------
	public ResultSet llistar_registres(String nom_taula) 
		{
		try {
			String wSentenciaSQL = "select * from " + nom_taula;
		//	System.out.println("Llençant llistat " + wSentenciaSQL);
			stmt = con.createStatement();
			rs = stmt.executeQuery(wSentenciaSQL);
			return rs;
			}
		catch (Exception e) 
			{
			System.out.println(e);
			return null;
			}
		}
	//-------------------------------------------------------------------------------------------------
	// Afegir un registre a una taula
	//-------------------------------------------------------------------------------------------------
	public boolean afegir_registre(String nom_taula, ArrayList<String> noms_camps, ArrayList<String> valors_camps) 
		{
		boolean resultat = false;
		try {
					
			String wSentenciaSQL = "insert into " + nom_taula + " (";
			
			int wIndex = noms_camps.size() - valors_camps.size();
			
			for (int x=wIndex;x<noms_camps.size();x++)
				{
			
				if (x==wIndex) {wSentenciaSQL = wSentenciaSQL + noms_camps.get(x); }
				else {wSentenciaSQL = wSentenciaSQL + "," + noms_camps.get(x);}
			
				if (x==(noms_camps.size()-1)) {wSentenciaSQL = wSentenciaSQL +")"; }
			
				}
			wSentenciaSQL = wSentenciaSQL + " values (";
		
			for (int x=0;x<valors_camps.size();x++)
				{
		
				if (x==0) {wSentenciaSQL = wSentenciaSQL + "'" + valors_camps.get(x) + "'"; }
				else {wSentenciaSQL = wSentenciaSQL + "," + "'" + valors_camps.get(x) + "'";}
		
				if (x==(valors_camps.size()-1)) {wSentenciaSQL = wSentenciaSQL +")"; }
		
				}
		
	//		System.out.println("Llençant inserció " + wSentenciaSQL);
		
			pstmt = con.prepareStatement(wSentenciaSQL);
			pstmt.execute();
	//		System.out.println(nom_taula +" insertada");
		
			resultat = true;
				
			return resultat;
			}
		catch (Exception e) 
			{
			System.out.println(e);
			return resultat;
			}		
		}
	//-------------------------------------------------------------------------------------------------
	// Modificar un registre
	//-------------------------------------------------------------------------------------------------
	
	public boolean modificar_registre(String nom_taula, String id_registre, ArrayList<String> noms_camps, ArrayList<String> valors_camps,String clau_principal) {
	
	boolean resultat = false;
	
	try {
		String wSentenciaSQL = "update " + nom_taula + " set ";
	
		for (int x=0;x<noms_camps.size();x++)
			{
		
			if (x==0) {wSentenciaSQL = wSentenciaSQL + noms_camps.get(x) + "='" + valors_camps.get(x) + "'";}
			else {wSentenciaSQL = wSentenciaSQL + "," + noms_camps.get(x) + "='" + valors_camps.get(x) + "'";}
		
		
			}
		
		wSentenciaSQL = wSentenciaSQL + " where " + clau_principal + "= '" + id_registre + "'";
	
		System.out.println("Llençant actualització " + wSentenciaSQL);
	
		pstmt = con.prepareStatement(wSentenciaSQL);
		pstmt.execute();
		resultat = true;
		System.out.println(nom_taula +" modificada");
		return resultat;
		}
	catch (Exception e) 
		{
		System.out.println(e);
		return false;
		}		
	}
	//-------------------------------------------------------------------------------------------------
	// Esborrar un registre
	//-------------------------------------------------------------------------------------------------	
	public boolean esborrar_registre(String nom_taula, String id_registre,String clau_principal) 
	{
	
	boolean resultat = false;
	
	try {
		String wSentenciaSQL= "delete from " + nom_taula + " where " + clau_principal + " = '" + id_registre + "'";
	//	System.out.println("Llençant esborrat " + wSentenciaSQL);
		pstmt = con.prepareStatement("delete from " + nom_taula + " where " + clau_principal + " = '" + id_registre + "'");
		pstmt.execute();
	
		resultat=true;
	
		return resultat;
		}
	catch (Exception e) 
		{
		System.out.println(e);
		return resultat;
		}		
	}
	//-------------------------------------------------------------------------------------------------
	// Desconnectar
	//-------------------------------------------------------------------------------------------------	
	public void tancar_connexio_BBDD()
	{
		try {con.close();}
		catch (Exception e) {System.out.println(e);}		
	}
	
	
}


