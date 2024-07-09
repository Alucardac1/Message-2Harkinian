package com.message_2harkinian.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import com.message_2harkinian.utils.Constants;

/*
 * The Interpreter class is the heart of the message 2harkinian, this class read, interpretate and convert the
 * message_data.h to message_data file from the 2ship2harkinian.
 */

public class Interpreter extends Constants {

	private Pattern isOpeningBracket = Pattern.compile("\\[*"); // this verification is necessary for compatibility with dialogues that contains
	private Matcher openingBracketMatcher;						// buttons, for example "use [A] for this action...".
	private String commandStack = ""; // this "stack" stores the commands presents between the dialogs for conversion to hex value.
	private boolean removeFFFD = true; // this variable removes the 0xFFFD section (the last section) from the conversion, because 2s2h doesn't
	// have this section in the end of file, it's just for hash matching.
	private File file; // path for the message_data.h file.	
	private JTextArea textArea; // the JTextArea is used here for visual feedback.
	
	// constructor, it's used for initialize the interpreter and the necessary components.
	public Interpreter(File file, JTextArea textArea) {
		this.file = file;
		this.textArea = textArea;

		Start();
	}
	
	public void Start() {
		HexWriter hw = new HexWriter();		// the hexwriter is the class that will write the 2sh2 message_data file.
		hw.writeHeader();					// write the 2sh2 main header.
		initializeCommandsHashMap(); 		// Initialize the Commands HashMap.
		initializeaspecialCharsHashMap();	// Initialize the Especial Chars HashMap.
		
		
		ArrayList<Byte> Section_ByteArray = new ArrayList<Byte>(); // ArrayList for converted hex data, this work line per line.
		boolean isEndOfSection = false; // Verifiy the "end of section" a section is all valid content that starts with "DEFINE_MESSAGE(" 
		// and ends with ")", except the eight hex fields of section header.
		// vv this pattern is for detect the begin of sections.
		Pattern DEFINE_MESSAGE = Pattern.compile("^DEFINE_MESSAGE\\(\\s*(0x[0-9a-fA-F]{4})\\s*,\\s*(0x[0-9a-fA-F]{2})\\s*,\\s*(\"\\\\x[0-9a-fA-F]{2}\\\\x[0-9a-fA-F]{2}\")\\s*(\"\\\\x[0-9a-fA-F]{2}\")\\s*(\"\\\\x[0-9a-fA-F]{2}\\\\x[0-9a-fA-F]{2}\")\\s*(\"\\\\x[0-9a-fA-F]{2}\\\\x[0-9a-fA-F]{2}\")\\s*(\"\\\\x[0-9a-fA-F]{2}\\\\x[0-9a-fA-F]{2}\")\\s*(\"\\\\x[0-9a-fA-F]{2}\\\\x[0-9a-fA-F]{2}\")\\s*$");
		// ^^ this pattern is for detect the begin of sections.
		Matcher DM_Matcher; // Matcher for Define Message.
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine(); // read the line.
			
			while (line != null) { // if the line is null (except in errors or other cases) it's indicate that the program has reached the end of file.
				DM_Matcher = DEFINE_MESSAGE.matcher(line); // search for the Define Message pattern (begin of section).
				
				if(DM_Matcher.find() && DM_Matcher.group(0).contains("DEFINE_MESSAGE")) {
					if (DM_Matcher.group(ADDRESS_FIELD).contains("0xFFFD") && removeFFFD) { // Ignores the 0xFFFD section and write 0xBF instead of data.
						textArea.append("Section " + DM_Matcher.group(ADDRESS_FIELD) + ":\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
						byte[] fffd = {(byte) 0xBF};
						switch (hw.writeHeaderData(fffd)) {
						case 0:
							textArea.append("This section is ommited on 2s2h!\n0xBF value wrote instead of the Section.\n");
							break;
						case 1:
							textArea.append("Error on write data\nof this section!\n");
							break;
						}
						textArea.setCaretPosition(textArea.getDocument().getLength());
						break;}
					
					// CByteArray contains the header of sections. The header iss the eight field values after "DEFINE_MESSAGE(".
						byte[] CByteArray = DM_Header_Conversor(DM_Matcher.group(ADDRESS_FIELD), DM_Matcher.group(UNUSED_FIELD),
											DM_Matcher.group(_1ST_HEX_FIELD), DM_Matcher.group(_2ND_HEX_FIELD), DM_Matcher.group(_3RD_HEX_FIELD),
											DM_Matcher.group(_4TH_HEX_FIELD), DM_Matcher.group(_5TH_HEX_FIELD), DM_Matcher.group(_6TH_HEX_FIELD));
						
						// Calls the writeHeaderData for write the data of headers converted in hex on the file.
	        		    textArea.append("Section " + DM_Matcher.group(ADDRESS_FIELD) + ":\n");
	        		    textArea.setCaretPosition(textArea.getDocument().getLength());
						switch (hw.writeHeaderData(CByteArray)) {
						case 0:
							textArea.append("Header wrote with sucess!\n");
							break;
						case 1:
							textArea.append("Error on write Header data\nof this section!\n");
							break;
						}
						textArea.setCaretPosition(textArea.getDocument().getLength());
					
				} else {
					// If the line is not a header, the section_ByteArray receive the section data.
					Section_ByteArray.addAll(DM_Section_Conversor(line));
					// This code calculates the size of section before to write the section data, this information is necessary to
					// 2ship2harkinian works, otherwise, the game will crash.
					if(line.equals(")")) {
						isEndOfSection = true;
					}
					
					if (Section_ByteArray.size() == 0 && isEndOfSection) {
						switch(hw.writeSizeData(intToLittleEndianBytes(1))) {
						case 0:
							textArea.append("Size data wrote with sucess!\n");
							break;
						case 1:
							textArea.append("Error on write Size data\nof this section!\n");
							break;
						}
						textArea.setCaretPosition(textArea.getDocument().getLength());
						isEndOfSection = false;
					} else if (Section_ByteArray.size() != 0 && isEndOfSection){

						switch(hw.writeSizeData(intToLittleEndianBytes(Section_ByteArray.size() + 1))) {
						case 0:
							textArea.append("Size data wrote with sucess!\n");
							break;
						case 1:
							textArea.append("Error on write Size data\nof this section!\n");
							break;
						}
						textArea.setCaretPosition(textArea.getDocument().getLength());

						// Write the section data.
						switch(hw.writeSectionData(Section_ByteArray)) {
						case 0:
							textArea.append("Section data wrote with sucess!\n");
							break;
						case 1:
							textArea.append("Error on write Section data\nof this section!\n");
							break;
						}
						
						isEndOfSection = false;
						Section_ByteArray.clear();
					}
					
				}
				line = br.readLine(); // read nextline while line != null
			}
			textArea.append("End of conversion.\n\nPut the generated message_data_static\nfile inside of a .otr file\nfollowing this hierarchy:\n"
					+ "text\\message_data_static\\");
			textArea.setCaretPosition(textArea.getDocument().getLength());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// This function receive the header fields and convert to a byte array.
	public byte[] DM_Header_Conversor(String Address, String Unused, String _1st_hex, String _2nd_hex, String _3rd_hex, String _4th_hex,
				String _5th_hex, String _6th_hex) {
		if (!Address.contentEquals("0x0000")) {
			byte[] byteArray = new byte[13];
			Matcher matcher;
			
			// the DEFINE_MESSAGE is indicated by the byte (0xBF)
			byteArray[0] = (byte) 0xBF;

			// Address field in little endian (0x[0-9 aA-fF] 0x[0-9 aA-fF])
			Pattern Address_pattern = Pattern.compile("^\\s*0x([0-9 aA-fF]{2})([0-9 aA-fF]{2})\\s*$");
			matcher = Address_pattern.matcher(Address);
			matcher.find();

			byteArray[1] = HexFormat.of().parseHex(matcher.group(2))[0];
			byteArray[2] = HexFormat.of().parseHex(matcher.group(1))[0];
			
			// The data bellow is in little endian, i not know if is all the data, but probably yes.
			// First hexString field
			Pattern _1st_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _1st_hex_pattern.matcher(_1st_hex);
			matcher.find();
			byteArray[3] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[4] = HexFormat.of().parseHex(matcher.group(2))[0];

			// Second hexString field
			Pattern _2nd_hex_pattern = Pattern.compile("^\\s*\\\"\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _2nd_hex_pattern.matcher(_2nd_hex);
			matcher.find();
			byteArray[5] = HexFormat.of().parseHex(matcher.group(1))[0];

			// in this position there is a byte 0x00, I suspect that it is the field after
			// the address that I called
			// "unused field", because in my tests changing this field did not result in any
			// difference in the game,
			// I am referencing this "unused field" as this byte, but when in doubt, keep it
			// as 0x00.

			Pattern Unused_pattern = Pattern.compile("^\\s*0x([0-9 aA-fF]{2})\\s*$");
			matcher = Unused_pattern.matcher(Unused);
			matcher.find();
			byteArray[6] = HexFormat.of().parseHex(matcher.group(1))[0];

			// Theese four fields are basically 0xFF values, changing them is apparently
			// irrelevant
			// it makes no difference to not know which field was omitted in 2S2H. When in
			// doubt, keep it as 0xFF.

			Pattern _3rd_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _3rd_hex_pattern.matcher(_3rd_hex);
			matcher.find();
			
			byteArray[8] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[7] = HexFormat.of().parseHex(matcher.group(2))[0];

			Pattern _4th_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _4th_hex_pattern.matcher(_4th_hex);
			matcher.find();
			
			byteArray[10] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[9] = HexFormat.of().parseHex(matcher.group(2))[0];

			Pattern _5th_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _5th_hex_pattern.matcher(_5th_hex);
			matcher.find();
			
			byteArray[12] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[11] = HexFormat.of().parseHex(matcher.group(2))[0];

			return byteArray;
		} else {

			byte[] byteArray = new byte[8];
			Matcher matcher;

			// the header of address 0x0000 does not have the first five bytes that the other addresses have,
			// so if the address is 0x0000 only the eight bytes below will be written.
			// this data still in big endian because the 0x0000 is empty and for coincidence the header is correct even in big endian.
			// if this section is necessary on your mod just flip each position that have 2 bytes on the byteArray bellow.
			
			Pattern _2nd_hex_pattern = Pattern.compile("^\\s*\\\"\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _2nd_hex_pattern.matcher(_2nd_hex);
			matcher.find();
			byteArray[0] = HexFormat.of().parseHex(matcher.group(1))[0];

			Pattern Unused_pattern = Pattern.compile("^\\s*0x([0-9 aA-fF]{2})\\s*$");
			matcher = Unused_pattern.matcher(Unused);
			matcher.find();
			byteArray[1] = HexFormat.of().parseHex(matcher.group(1))[0];

			Pattern _3rd_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _3rd_hex_pattern.matcher(_3rd_hex);
			matcher.find();
			byteArray[2] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[3] = HexFormat.of().parseHex(matcher.group(2))[0];

			Pattern _4th_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _4th_hex_pattern.matcher(_4th_hex);
			matcher.find();
			byteArray[4] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[5] = HexFormat.of().parseHex(matcher.group(2))[0];

			Pattern _5th_hex_pattern = Pattern.compile("^\\\"\\\\x([0-9 aA-fF]{2})\\\\x([0-9 aA-fF]{2})\\\"\\s*$");
			matcher = _5th_hex_pattern.matcher(_5th_hex);
			matcher.find();
			byteArray[6] = HexFormat.of().parseHex(matcher.group(1))[0];
			byteArray[7] = HexFormat.of().parseHex(matcher.group(2))[0];

			return byteArray;
		}
	}
	
	// This function receive the line of sections and convert to a Byte ArrayList.
	public ArrayList<Byte> DM_Section_Conversor(String line) {
		boolean stringBegin = false;
		boolean isBackslashCommand = false;
		boolean caseX = false;
		boolean verifyIfIsButton = false;
		String caseXStack = "";
		String verifyIfIsButtonStack = "";
		int caseX_iterator = 0;
		ArrayList<Byte> arrayList = new ArrayList<Byte>();
		char[] charList = line.toCharArray();
		for(int i = 0; i < charList.length ; i++){
			
			
			if(charList[i] == 0x22 && !isBackslashCommand) {
				stringBegin = !stringBegin;
				String[] s = separateCommands(commandStack);
				for (String t : s) {
					if (commands.get(t) != null) {
						arrayList.add((byte) Integer.parseUnsignedInt(commands.get(t), 16));
					}
				}
				commandStack = "";

			}
			// If is a start of a string indicated by ". use \" in dialogs if you want to use ".
			if (stringBegin) {	
				// Verify if [] in dialogs is a button or not.
				if (verifyIfIsButton) {
					verifyIfIsButtonStack += Character.toString(charList[i]);
					if (charList[i] == ']' || Character.isWhitespace(charList[i])) {
						if (specialChars.containsKey(verifyIfIsButtonStack)) {
							arrayList.add((byte) Integer.parseUnsignedInt(specialChars.get(verifyIfIsButtonStack), 16));
						} else {
							for (char c : verifyIfIsButtonStack.toCharArray()) {
								arrayList.add((byte) c);
							}
						}
						verifyIfIsButton = false;
						verifyIfIsButtonStack = "";
					}
					continue;
				}
				
				if (charList[i] == '[') {
					// Button codes, it's just for compatibility with extracted
					// message_data_static.h files from n64.
					// I recommend the usage of CMD_BTN_ instead of this.
					verifyIfIsButton = true;
					verifyIfIsButtonStack = Character.toString(charList[i]);
					continue;
				}
				
				// start the verification of the backslash bar;
				if(charList[i] == '\\' && !isBackslashCommand) {
					
					isBackslashCommand = true;
					continue;
				}
				
				// Backslash commands must be on strings
				if (isBackslashCommand) {
					
					if(caseX) {
						switch (caseX_iterator) {
						case 0:
							caseXStack = Character.toString(charList[i]);
							caseX_iterator++;
							continue;
						case 1:
							caseXStack += Character.toString(charList[i]);
							arrayList.add((byte) Integer.parseUnsignedInt(caseXStack, 16));
							caseX_iterator = 0;
							isBackslashCommand = false;
							caseX = false;
							continue;
						}
						
					}
					
					switch (charList[i]) {
					
					case 'x':
						caseX = true;
						break;

					case '\\':
						// add the byte of inversal bar on the arraylist.
						arrayList.add((byte) 0x2F); // 0x2F is a normal bar, the \ does't exist in 2s2h chars.
						isBackslashCommand = false;
						break;

					case 'n':
						// add the byte of line break on arraylist.
						arrayList.add((byte) 0x11);
						isBackslashCommand = false;
						break;
						
					case 0x22:
						// add the byte of double quote to arraylist.
						arrayList.add((byte) 0x22);
						isBackslashCommand = false;
						break;
						
					default:
						isBackslashCommand = false;
						break;
					}

					continue; // Go to next while iteration.
				}
				
				
				// detection of special characters for non english alphabets. (roman alphabets only for now).
				if(!(charList[i] == 0x22) && !specialChars.containsKey(Character.toString(charList[i])))
					arrayList.add((byte) charList[i]);
				else if(specialChars.containsKey(Character.toString(charList[i]))){
					arrayList.add((byte) Integer.parseUnsignedInt(specialChars.get(Character.toString(charList[i])),16));
				}

			} else {
				// verify if the "[" is a button command, i don't remember why i put it here,
				// but probably is for avoid bugs, like wrong detections.
				openingBracketMatcher = isOpeningBracket.matcher(verifyIfIsButtonStack);

				if (openingBracketMatcher.find() && verifyIfIsButton) {
					for (char c : verifyIfIsButtonStack.toCharArray())
						arrayList.add((byte) c);
					verifyIfIsButton = false;
					verifyIfIsButtonStack = "";
					continue;
				}
				
				// this code is for non strings commands.
				if(!Character.isWhitespace(charList[i]) && charList[i] != '(' && charList[i] != ')') {
					commandStack += charList[i]; // the commandStack store the commands char-by-char.
					continue;
				}

				String[] s = separateCommands(commandStack); // it's for the cases on the commands will stored
				// together.
				for (String t : s) {
					if (commands.get(t) != null) {
						arrayList.add((byte) Integer.parseUnsignedInt(commands.get(t), 16));
					}
				}
				commandStack = ""; // Clean the commandStack.
				
			}
		}
		
		return arrayList;
	}
	// This function converts a int number in four bytes (little endian), it's used on section data size calculation.
	public byte[] intToLittleEndianBytes(int number) {
	    byte[] bytes = new byte[4];
	    bytes[0] = (byte) (number & 0xFF);
	    bytes[1] = (byte) ((number >> 8) & 0xFF);
	    bytes[2] = (byte) ((number >> 16) & 0xFF);
	    bytes[3] = (byte) ((number >> 24) & 0xFF);

	    return bytes;
	}
	
	// This fuction is used to separate commands that was put together on the commandStack.
	public String[] separateCommands(String input) {
        input = input.replaceAll("[^A-Z0-9_]", "");

        ArrayList<String> multiCommandStack = new ArrayList<>();
        StringBuilder currentCommand = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            if (i + 4 <= input.length() && input.substring(i, i + 4).equals("CMD_")) {
                // If "CMD_" was found, put the actual command in the array and restart the StringBuilder.
                if (currentCommand.length() > 0) {
                    multiCommandStack.add(currentCommand.toString());
                }
                currentCommand = new StringBuilder();
            }
            // put the char in current command.
            currentCommand.append(input.charAt(i));
        }
        
        // put the last command in the array, if was one.
        if (currentCommand.length() > 0) {
            multiCommandStack.add(currentCommand.toString());
        }

        // Converts the ArrayList on a String array.
        String[] result = new String[multiCommandStack.size()];
        result = multiCommandStack.toArray(result);

        return result;
    }

}