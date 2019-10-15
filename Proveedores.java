package pla4CRUD;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.Scanner;
	import java.util.logging.Level;
	import java.awt.*;

	import com.sun.istack.internal.logging.Logger;

	//---------------------------------------------------------------------

	public class Proveedores implements GestioTaula{
		
		private static Connection connexio;						//s'usa per obtenir l'accés a l'estructura de la BBDD
		private static Statement instruccio_SQL;
		private static String host="localhost", base_dades="gestor", nom_usuari="root", password="Cifo2019";
		private static ResultSetMetaData rsmetadatos;
		
		String nom_taula="proveedores";
		String clau_principal="pro_id";
		
		ArrayList<String> nom_columna = new ArrayList<String>();			// Nom de la columna
		ArrayList<String> tipus_dada = new ArrayList<String>();				// Nom de la columna
		ArrayList<Integer> llargada_columna = new ArrayList<Integer>(); 	// per al numeros
		ArrayList<String> etiqueta_columna = new ArrayList<String>();		// Nom de la columna
		
		ResultSet rs;
		
		AccesoADatos GestioBBDD= new AccesoADatos();						// Obre la connexió a la BBDD
		
		//
		//----------------------------------------------------------------------- Constructor
		//
			public Proveedores(AccesoADatos wGestioBBDD) 
			{
			
			GestioBBDD = wGestioBBDD;											// Instancia el modul d'accès a la BBDD
				
			// EstructuraBD.ObtenirEstructuraTaula(nom_taula);
				
			try {
	            
				// Obtenir columnes i tipus de la taula
	            
				Class.forName("com.mysql.jdbc.Driver");
	            connexio = DriverManager.getConnection("jdbc:mysql://"+host+"/"+base_dades, nom_usuari, password);
	            instruccio_SQL = connexio.createStatement();
	            
	            ResultSet rs = instruccio_SQL.executeQuery("select * from " + nom_taula);
	            rsmetadatos =  rs.getMetaData();
	           
	            //obtenint nombre de columnes
	            
	            int col = rsmetadatos.getColumnCount();
	           // System.out.println("Columnas: "+col);
	            
	            // Obtenint l'estructura de la taula
	            
	            for(int i=1;i<=col;i++)
	            	{
	                nom_columna.add(rsmetadatos.getColumnName(i));
	                tipus_dada.add(rsmetadatos.getColumnTypeName(i));
	                llargada_columna.add(rsmetadatos.getColumnDisplaySize(i));
	                etiqueta_columna.add(rsmetadatos.getColumnLabel(i));
	         //     System.out.println("Relacions:" + rsmetadatos.getCrossReference("","","","", "", "");)
	            	}       
	        	} 
				catch (Exception ex) 
				{
					Logger.getLogger(Clientes.class.getName(), null).log(Level.SEVERE, null, ex);
				}
			
			}
		
		//
		//---------------------------------------------------------------------  Alta de nou registre
		//
			
		public void nou_registre() {												
		
		ArrayList<String> valors_camps = new ArrayList<String>();
		Scanner valor_entrat = new Scanner(System.in);
		System.out.print(nom_columna.get(0) + "\t : ");
		String wValorEntrat = valor_entrat.nextLine();
		
		if (GestioBBDD.existeix_registre(nom_taula, wValorEntrat,clau_principal))
			{
			System.out.println("El identificador " + wValorEntrat + " ya existeix.");
			Toolkit.getDefaultToolkit().beep();
			}
		else 
			{
			valors_camps.add(wValorEntrat);
			for (int c=1;c<nom_columna.size();c++)      //(String columna:nom_columna) 
				{
				System.out.print(nom_columna.get(c) + "\t : ");
				valors_camps.add(valor_entrat.nextLine());
				}
			if (GestioBBDD.afegir_registre(nom_taula, nom_columna, valors_camps))
				{System.out.println("El registre s'ha donat d'alta correctament.");}
			else
				{
				Toolkit.getDefaultToolkit().beep();
				System.out.println("Alguna cosa ha anat malament");}
				}
		}
		//
		//---------------------------------------------------------------------  Esborrar un registre
		//
		public void esborrar_registre(){
			
			Scanner valor_entrat = new Scanner(System.in);
			System.out.println("Introdueix el codi (CNI/CIF) :");
			String wValorEntrat = valor_entrat.nextLine();
			
			if (GestioBBDD.existeix_registre(nom_taula, wValorEntrat,clau_principal))
				{
				if (GestioBBDD.esborrar_registre(nom_taula, wValorEntrat,clau_principal))
				{
					System.out.println("Registre esborrat.");
					} 
				else 
					{
					Toolkit.getDefaultToolkit().beep();
					System.out.println("Hi ha hagut algun problema.");
					}
				}
			
			else 
				{
				System.out.println("El identificador " + wValorEntrat + " no existeix.");
				Toolkit.getDefaultToolkit().beep();
				}
			}
		
		//
		//--------------------------------------------------------------------- Modificar un registre
		//
		public void modificar_registre(){
			
			ArrayList<String> valors_camps = new ArrayList<String>();
			String wValorEntrat="";
			
			Scanner valor_entrat = new Scanner(System.in);
			System.out.println("Introdueix el codi a modificar (CNI/CIF) :");
			wValorEntrat = valor_entrat.nextLine();
			
			if (GestioBBDD.existeix_registre(nom_taula, wValorEntrat,clau_principal))
				{
				try {
					rs=GestioBBDD.consultar_registre(nom_taula, wValorEntrat,clau_principal);
					valors_camps.add(wValorEntrat);
					for (int x=1;x<nom_columna.size();x++)
						{
						System.out.println(nom_columna.get(x) + "\t : " +rs.getString(nom_columna.get(x)) + " ###  Introdueix el nou valor : ");
						wValorEntrat = valor_entrat.nextLine();
						if (wValorEntrat.length()==0)
							{valors_camps.add(rs.getString(nom_columna.get(x)));}
						else
							{valors_camps.add(wValorEntrat);}
						}
				
					if (GestioBBDD.modificar_registre(nom_taula, valors_camps.get(0), nom_columna, valors_camps,clau_principal))
						{
						System.out.println("Registre actualitzat");
						}
					else
						{
						Toolkit.getDefaultToolkit().beep();
						System.out.println("Alguna cosa ha anat malament");
						}
					} 
				catch (SQLException e) 
					{
					e.printStackTrace();
					Toolkit.getDefaultToolkit().beep();
					System.out.println("Alguna cosa ha anat malament");
					}
				}
			else
				{
				Toolkit.getDefaultToolkit().beep();
				System.out.println("El registre no existeix");
				}

			
		};
		//
		//--------------------------------------------------------------------- Consultar un registre
		//
		public void visualitzar_registre()
			{
			Scanner valor_entrat = new Scanner(System.in);
			System.out.println("Introdueix el codi (CNI/CIF) :");
			String wValorEntrat = valor_entrat.nextLine();
			
			if (GestioBBDD.existeix_registre(nom_taula, wValorEntrat,clau_principal))
				{
				try {
					rs=GestioBBDD.consultar_registre(nom_taula, wValorEntrat,clau_principal);
					for (String columna:nom_columna) 
						{
						System.out.println(columna + "\t : " +rs.getString(columna));
						}
					System.out.println("");
					} 
				catch (SQLException e) 
					{
					e.printStackTrace();
					}
				}
			else
				{
				Toolkit.getDefaultToolkit().beep();
				System.out.println("El registre no existeix");
				}
			}
		
		//--------------------------------------------------------------------- Llistar
		
		public void llistar_registres()
			{
			
			rs=GestioBBDD.llistar_registres(nom_taula);
			
			for (String columna:nom_columna) {System.out.print(" <" +columna + "> " + "\t\t"); }
			System.out.println(""); 
			
			try {
				while (rs.next()) 
					{
					for (String columna:nom_columna) 
						{
						System.out.print(" <" +rs.getString(columna) + "> "+ "\t\t");
						}
					System.out.println("");
					}
				} 
				catch (SQLException e) 
				{
				
				e.printStackTrace();
				}
			};

	}
