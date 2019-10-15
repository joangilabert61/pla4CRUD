package pla4CRUD;

import java.util.Scanner;

public class programa {
	
		public static void main(String[] args) 
		{
		String[] nom_taules = {"Salir","clientes","proveedores","productos","ventas"};
		String[] opcions_disponibles = {"Salir","alta","baja","modificacion","consulta","listado por pantalla"};
		
				
		AccesoADatos GestioBBDD = new AccesoADatos();
		
		GestioBBDD.Connectar_BBDD();
		
		Clientes client= new Clientes(GestioBBDD); 
		Proveedores proveidor= new Proveedores(GestioBBDD);
		Productos articles= new Productos(GestioBBDD);
		Ventas vendes= new Ventas(GestioBBDD);
		
		int num_taula=0;
		int num_funcio=0;
		//------------------------------------------------------------------------------------------------------------------------------------
		//                        selecció de taula i funció
		//-------------------------------------------------------------------------------------------------------------------------------------
			int opcioActual=0;									//Inicialitzem opció escoliida del menú
			do 
			{
				opcioActual=escollirTaula(nom_taules,opcions_disponibles);						//mostrem y seleccionem la funció del menú
				

			//	System.out.println("Opcio actual " + opcioActual);
				
				num_taula=((opcioActual/10));
				num_funcio=((opcioActual%10));
				
			//	System.out.println("Ha escogido " + opcions_disponibles[num_funcio] + " de " + nom_taules[(num_taula)]+".");
				
			//	EstructuraTabla();
				
			//	System.out.println("num_taula" + num_taula + " --- num_funcio = " + num_funcio);
			//	EstructuraBD.ObtenirEstructuraTaula(nom_taules[num_taula-1]);
			
				
			if (opcioActual!=0)	
				{
				switch (num_taula)
					{
					case 1:
						switch (num_funcio)
							{
						case 1:
							client.nou_registre();
							break;
						case 2:
							client.esborrar_registre();
							break;
						case 3:
							client.modificar_registre();
							break;
						case 4:
							client.visualitzar_registre();
							break;
						case 5:
							client.llistar_registres();
							break;
						case 9:
							break;
						default:
							System.out.println("Opció erronia");
							}
						break;
					case 2:
						switch (num_funcio)
							{
							case 1:
								proveidor.nou_registre();
								break;
							case 2:
								proveidor.esborrar_registre();
								break;
							case 3:
								proveidor.modificar_registre();
								break;
							case 4:
								proveidor.visualitzar_registre();
								break;
							case 5:
								proveidor.llistar_registres();
								break;
							case 9:
								break;
							default:
								System.out.println("Opció erronia");
							}
							break;
					case 3:
						switch (num_funcio)
							{
							case 1:
								articles.nou_registre();
								break;
							case 2:
								articles.esborrar_registre();
								break;
							case 3:
								articles.modificar_registre();
								break;
							case 4:
								articles.visualitzar_registre();
								break;
							case 5:
								articles.llistar_registres();
								break;
							case 9:
								break;
							default:
								System.out.println("Opció erronia");
							}
							break;
					case 4:
						switch (num_funcio)
							{
							case 1:
								vendes.nou_registre();
								break;
							case 2:
								vendes.esborrar_registre();
								break;
							case 3:
								vendes.modificar_registre();
								break;
							case 4:
								vendes.visualitzar_registre();
								break;
							case 5:
								vendes.llistar_registres();
								break;
							case 9:
								break;
							default:
								System.out.println("Opció erronia");
							}
						break;
					case 9:
						break;
					default:
						System.out.println("Taula erronia");
					}
				}
			}
			while (num_taula != 0);

		}
		//-------------------------------------------------------------------------------------------------------------------------------------------------
		// Comprova que s'introdueix un número i aquest es sencer i vàlid
		//-------------------------------------------------------------------------------------------------------------------------------------------------
			private static boolean esNumeroSencer(String cadena)
				{
				try 
					{Integer.parseInt(cadena);
					return true;
					} 
				catch (NumberFormatException nfe)
					{return false;}
				}	
		
			//-------------------------------------------------------------------------------------------------------------------------------------------------
			// Mètode per crear opcions, demanar-les per pantalla, validarles i muntar al MAIN un case d'execució segons la opció
			//-------------------------------------------------------------------------------------------------------------------------------------------------	
			private static int escollirTaula(String[] nom_taules, String[] opcions_disponibles) 
			{
				String cadena1="";
				int valorEntrat = 0;
					
				Scanner entrada = new Scanner(System.in);
					
				do
					{
					System.out.println("Escoje una tabla para mantener o pulsa 0 para salir.");
					for (int taula=1;taula<nom_taules.length; taula++)
						{
						System.out.println(taula + " -> " + nom_taules[taula]);
						}
					cadena1=entrada.nextLine();
						
					if (esNumeroSencer(cadena1))
						{
						valorEntrat=Integer.parseInt(cadena1);
						//System.out.print("Opció escollida: " + valorEntrat);
						
						int num_maxim_taules = nom_taules.length - 1; 
							
						switch (valorEntrat)
							{
							case 0:
								System.out.println("Programa finalitzat.");
								break;
								
							default:
								if (valorEntrat <= num_maxim_taules) 
									{
									//System.out.println(" -> " + nom_taules[valorEntrat-1]);
									int funcio = escollirFuncio(opcions_disponibles);
									
									//System.out.println(" -> " + ((valorEntrat-1)*10 + (funcio-1)));
									return ((valorEntrat)*10 + funcio);
									
									}
								else 
									{
									System.out.println("Opció incorrecta");
									}
							}								
						}			
					else
						{
						System.out.println(cadena1 + " no és un nombre vàlid.");
						valorEntrat=99;
						
						}
					}
					while (valorEntrat != 0);
				
					return valorEntrat;

				}
			//-------------------------------------------------------------------------------------------------------------------------------------------------
			// Mètode per crear opcions, demanar-les per pantalla, validarles i muntar al MAIN un case d'execució segons la opció
			//-------------------------------------------------------------------------------------------------------------------------------------------------	
			private static int escollirFuncio(String[] opcions_disponibles) 
			{
				String cadena1="";
				int valorEntrat = 9;
					
				Scanner entrada = new Scanner(System.in);
					
				do
					{
					System.out.println("Escoje la función o pulsa 0 para salir.");
					for (int opcio=1;opcio<opcions_disponibles.length; opcio++)
						{
						System.out.println(opcio + " -> " + opcions_disponibles[opcio]);
						}
					cadena1=entrada.nextLine();
						
					if (esNumeroSencer(cadena1))
						{
						valorEntrat=Integer.parseInt(cadena1);
						int num_maxim_opcions = opcions_disponibles.length-1; 
							
						switch (valorEntrat)
							{
							case 0:
								System.out.println("Cancelado.");
								break;
								
							default:
								if (valorEntrat <= num_maxim_opcions) 
									{
									//System.out.println(" -> " + opcions_disponibles[valorEntrat-1]);
									return valorEntrat;
									}
								else 
									{
									System.out.println("Opció incorrecta");
									}
							}								
						}			
					else
						{
						System.out.println(cadena1 + " no és un nombre vàlid.");
						valorEntrat=99;
						
						}
					}
					while (valorEntrat != 0);
				
					return valorEntrat;

				}

	}