package com.mycompany.karaofix;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Copia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Path path=Paths.get("C:\\Users\\TIC\\Downloads\\KaraoFix-master\\karaofix\\src\\main\\resources\\temazos");
		Path pathCopia=Paths.get("C:\\Users\\TIC\\Downloads\\KaraoFix-master\\karaofix\\src\\main\\resources\\grandesexistos");
		
		//Path path=Paths.get("C:\\Users\\Win7\\Downloads\\KaraoFix-master\\karaofix\\src\\main\\resources\\temazos");
		//Path pathCopia=Paths.get("C:\\Users\\Win7\\Downloads\\KaraoFix-master\\karaofix\\src\\main\\resources\\grandesexistos");
		
		try {
			//Listado de ficheros de canciones
			List<Path> ficheros = Files.list(path).collect(Collectors.toList());
			for(Path cancion:ficheros) {
				
				//Direccion de fichero
				Path Npath=Paths.get(cancion.toString());
				
				//Nombre de la carpeta
				int barra = cancion.toString().lastIndexOf("\\");
				String Ncancion = cancion.toString().substring(barra + 1);
				
				//Crear carpeta destino
				Path carpeta = Paths.get(pathCopia.toString() + "\\" +Ncancion+"\\");
				File folder = new File(carpeta.toString());
				folder.mkdirs();
				
				//Direccion de la cancion
				Npath=Paths.get(cancion.toString() +"\\"+ Ncancion + ".txt");
				
				List<Path> ListImg = Files.list(cancion).collect(Collectors.toList());
				String Imagen = null;
				String EndImagen = null;
				String Cimagen = null;
				int i = 1;
				for(Path Img:ListImg) {
					if(i == 1) {

						barra = Img.toString().lastIndexOf("\\");
						EndImagen = Img.toString().substring(barra + 1);
						i++;
						}
					}
				i = 1;
				//Direccion de la imagen
				Imagen=cancion.toString()+"\\"+EndImagen;
				//Direccion destino de la imagen
				Cimagen=pathCopia.toString()+"\\"+Ncancion+"\\"+EndImagen;
				
				//Direccion destino de la cancion
				Path NpathCopia = Paths.get(pathCopia.toString()+"\\"+Ncancion+"\\"+ Ncancion +".txt");
				
				Charset charset = Charset.forName("iso-8859-1");
					try (BufferedReader reader = Files.newBufferedReader(Npath,charset);
						PrintWriter writer = new PrintWriter(Files.newBufferedWriter(NpathCopia));
						FileInputStream fregis = new FileInputStream(Imagen); 
						FileOutputStream fsalida = new FileOutputStream(Cimagen, true);) {
					String line = null;
					line = reader.readLine();
					while (line != null) {
						
						//Eliminar "," si existe y copiar linea
						if((line.startsWith("#BPM") || line.startsWith("#GAP") )&& line.lastIndexOf(",") != -1) {
							int coma = line.lastIndexOf(",");
							String Nline = null;
							Nline=line.substring(0,coma);
							writer.println(Nline);
							}else {
						writer.println(line);
							}
						line = reader.readLine();
						}
					
						//Copiar imagen
						int b = fregis.read(); 
						while (b != -1) { 
							fsalida.write(b); 
							b = fregis.read(); 
						} 
					}catch (IOException x) {
							System.err.format("IOException: %s%n", x);
						}
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
