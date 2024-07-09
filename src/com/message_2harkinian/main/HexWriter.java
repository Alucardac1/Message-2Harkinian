package com.message_2harkinian.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HexWriter {

	public HexWriter() {

	}
	
	public int writeHeader() {

		final String header = "000000004d58544f00000000efbeadde" // Address 0x00000000
					  		+ "efbeadde000000000000000000000000" // Address 0x00000010
					  		+ "00000000000000000000000000000000" // Address 0x00000020
					  		+ "00000000000000000000000000000000" // Address 0x00000030
					  		+ "ec11000000000000";//----Data----" // Address 0x00000040

		int lenght = header.length();
		byte[] data = new byte[lenght / 2];
		for (int i = 0; i < lenght; i += 2) {
			data[i / 2] = (byte) ((Character.digit(header.charAt(i), 16) << 4)
					+ Character.digit(header.charAt(i + 1), 16));
		}

		try (FileOutputStream fos = new FileOutputStream("message_data_static", false)) { // "message_data_static" is the name of the file, "false" is for
																			 // not append data. The header must be the first
																			 // data on the file.
			fos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		return 0;

	}

	public int writeHeaderData(byte[] byteArray) {
		// Tenta escrever os bytes no arquivo
		try (FileOutputStream fos = new FileOutputStream("message_data_static", true)) { // "message_data_static" is the name file, "true" is for
																			// append data.
			for (byte b : byteArray) {
                String hexString = String.format("%02X", b); // Converts the byte for hexadecimal
                int hexValue = Integer.parseInt(hexString, 16); // Converts the hexadecimal string to numeric value
                fos.write(hexValue);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}
	
	public int writeSizeData(byte[] byteArray) {
		// Tenta escrever os bytes no arquivo
		try (FileOutputStream fos = new FileOutputStream("message_data_static", true)) { // "message_data_static" is the name file, "true" is for
																			// append data.
			for (byte b : byteArray) {
                String hexString = String.format("%02X", b); // Converts the byte for hexadecimal
                int hexValue = Integer.parseInt(hexString, 16);// Converts the hexadecimal string to numeric value
                fos.write(hexValue);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}
	
	public int writeSectionData(ArrayList<Byte> byteList) {
        // Tenta escrever os bytes no arquivo
        try (FileOutputStream fos = new FileOutputStream("message_data_static", true)) { // "message_data_static" is the name file, "true" is for
			// append data.
            for (Byte b : byteList) {
                // The value b is already a byte, so it's necessary to convert him for hexadecimal and convert it again to numeric value.
                fos.write(b);
            }

        } catch (IOException e) {
        	e.printStackTrace();
            return 1;
        }

        return 0;
    }
	
	

}
