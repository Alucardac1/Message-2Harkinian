package com.message_2harkinian.utils;

import java.util.HashMap;

public class Constants {

	// Interpreter Class Constants
	public final int
	ADDRESS_FIELD = 1,
	 UNUSED_FIELD = 2,
   _1ST_HEX_FIELD = 3,
   _2ND_HEX_FIELD = 4,
   _3RD_HEX_FIELD = 5,
   _4TH_HEX_FIELD = 6,
   _5TH_HEX_FIELD = 7,
   _6TH_HEX_FIELD = 8;
	
	

	// Hashmap for commands conversion.
	public HashMap <String, String> commands = new HashMap<String,String>();
	public HashMap <String, String> specialChars = new HashMap<String,String>();
	
		// Characters with Accents
		// In modern systems chars with accents are represented with two bytes, however, is necessary
		// to refer an only char with only byte in the game accent by accent.
		// Trema and Umlaut are visually similar and will be called TREMA_UMLAUT in this program.
	
	public void initializeaspecialCharsHashMap() {
		specialChars.put("á", "98"); // LOWERCASE_A_ACUTE
		specialChars.put("â", "99"); // LOWERCASE_A_CIRCUMFLEX
		specialChars.put("à", "97"); // LOWERCASE_A_GRAVE
		specialChars.put("ä", "9A"); // LOWERCASE_A_TREMA_UMLAUT
		specialChars.put("ã", "9A"); // LOWERCASE_A_TILDE (PROVISÓRIO)
		specialChars.put("Á", "81"); // UPPERCASE_A_ACUTE
		specialChars.put("Â", "82"); // UPPERCASE_A_CIRCUMFLEX
		specialChars.put("À", "80"); // UPPERCASE_A_GRAVE
		specialChars.put("Ä", "83"); // UPPERCASE_A_TREMA_UMLAUT
		specialChars.put("Ã", "83"); // UPPERCASE_A_TILDE
		specialChars.put("ç", "9B"); // LOWERCASE_C_CEDILLA
		specialChars.put("Ç", "84"); // UPPERCASE_C_CEDILLA
		specialChars.put("é", "9D"); // LOWERCASE_E_ACUTE
		specialChars.put("ê", "9E"); // LOWERCASE_E_CIRCUMFLEX
		specialChars.put("è", "9C"); // LOWERCASE_E_GRAVE
		specialChars.put("ë", "9F"); // LOWERCASE_E_TREMA_UMLAUT
		specialChars.put("É", "86"); // UPPERCASE_E_ACUTE
		specialChars.put("Ê", "87"); // UPPERCASE_E_CIRCUMFLEX
		specialChars.put("È", "20"); // UPPERCASE_E_GRAVE
		specialChars.put("Ë", "85"); // UPPERCASE_E_TREMA_UMLAUT
		specialChars.put("í", "A1"); // LOWERCASE_I_ACUTE
		specialChars.put("î", "A2"); // LOWERCASE_I_CIRCUMFLEX
		specialChars.put("ì", "A0"); // LOWERCASE_I_GRAVE
		specialChars.put("ï", "A3"); // LOWERCASE_I_TREMA_UMLAUT
		specialChars.put("Í", "8A"); // UPPERCASE_I_ACUTE
		specialChars.put("Î", "8B"); // UPPERCASE_I_CIRCUMFLEX
		specialChars.put("Ì", "20"); // UPPERCASE_I_GRAVE
		specialChars.put("Ï", "8C"); // UPPERCASE_I_TREMA_UMLAUT
		specialChars.put("ñ", "A4"); // LOWERCASE_N_TILDE
		specialChars.put("Ñ", "8D"); // UPPERCASE_N_TILDE
		specialChars.put("ó", "A6"); // LOWERCASE_O_ACUTE
		specialChars.put("ô", "A7"); // LOWERCASE_O_CIRCUMFLEX
		specialChars.put("ò", "20"); // LOWERCASE_O_GRAVE
		specialChars.put("ö", "A8"); // LOWERCASE_O_TREMA_UMLAUT
		specialChars.put("õ", "A8"); // LOWERCASE_O_TILDE
		specialChars.put("Ó", "8F"); // UPPERCASE_O_ACUTE
		specialChars.put("Ô", "20"); // UPPERCASE_O_CIRCUMFLEX
		specialChars.put("Ò", "8E"); // UPPERCASE_O_GRAVE
		specialChars.put("Ö", "20"); // UPPERCASE_O_TREMA_UMLAUT
		specialChars.put("Õ", "20"); // UPPERCASE_O_TILDE
		specialChars.put("ú", "AA"); // LOWERCASE_U_ACUTE
		specialChars.put("û", "AB"); // LOWERCASE_U_CIRCUMFLEX
		specialChars.put("ù", "A9"); // LOWERCASE_U_GRAVE
		specialChars.put("ü", "AC"); // LOWERCASE_U_TREMA_UMLAUT
		specialChars.put("ũ", "AC"); // LOWERCASE_U_TILDE
		specialChars.put("Ú", "93"); // UPPERCASE_U_ACUTE
		specialChars.put("Û", "94"); // UPPERCASE_U_CIRCUMFLEX
		specialChars.put("Ù", "92"); // UPPERCASE_U_GRAVE
		specialChars.put("Ü", "95"); // UPPERCASE_U_TREMA_UMLAUT
		specialChars.put("Ũ", "95"); // UPPERCASE_U_TILDE
		specialChars.put("ý", "20"); // LOWERCASE_Y_ACUTE
		specialChars.put("ŷ", "20"); // LOWERCASE_Y_CIRCUMFLEX
		specialChars.put("ÿ", "20"); // LOWERCASE_Y_TREMA_UMLAUT
		specialChars.put("Ý", "20"); // UPPERCASE_Y_ACUTE
		specialChars.put("Ŷ", "20"); // UPPERCASE_Y_CIRCUMFLEX
		specialChars.put("Ÿ", "20"); // UPPERCASE_Y_TREMA_UMLAUT
		
		specialChars.put("-", "2D");
		
		// Buttons dialog
		// is the same of the commands bellow, but this format is useful to message_data_static.h n64 format.
		// commands.put("CMD_BTN_A", "B0");
		// commands.put("CMD_BTN_B", "B1");
		// commands.put("CMD_BTN_C", "B2");
		// commands.put("CMD_BTN_L", "B3");
		// commands.put("CMD_BTN_R", "B4");
		// commands.put("CMD_BTN_Z", "B5");
		// commands.put("CMD_BTN_CUP", "B6");
		// commands.put("CMD_BTN_CDOWN", "B7");
		// commands.put("CMD_BTN_CLEFT", "B8");
		// commands.put("CMD_BTN_CRIGHT", "B9");
		// commands.put("CMD_Z_TARGET", "BA");
		// commands.put("CMD_CONTROL_PAD", "BB");
		
		specialChars.put("[A]", "B0");
		specialChars.put("[B]", "B1");
		specialChars.put("[C]", "B2");
		specialChars.put("[L]", "B3");
		specialChars.put("[R]", "B4");
		specialChars.put("[C-Up]", "B6");
		specialChars.put("[C-Down]", "B7");
		specialChars.put("[C-Left]", "B8");
		specialChars.put("[C-Right]", "B9");
		specialChars.put("[Z]", "B5");
		specialChars.put("[Control-Pad]", "BB");
	}
	
	public void initializeCommandsHashMap() {
		commands.put("CMD_COLOR_DEFAULT", "00");
		commands.put("CMD_COLOR_RED", "01");
		commands.put("CMD_COLOR_GREEN", "02");
		commands.put("CMD_COLOR_BLUE", "03");
		commands.put("CMD_COLOR_YELLOW", "04");
		commands.put("CMD_COLOR_LIGHTBLUE", "05");
		commands.put("CMD_COLOR_PINK", "06");
		commands.put("CMD_COLOR_SILVER", "07");
		commands.put("CMD_COLOR_ORANGE", "08");
		commands.put("CMD_TEXT_SPEED", "0A");
		commands.put("CMD_HS_BOAT_ARCHERY", "0B");
		commands.put("CMD_STRAY_FAIRIES", "0C");
		commands.put("CMD_TOKENS", "0D");
		commands.put("CMD_POINTS_TENS", "0E");
		commands.put("CMD_POINTS_THOUSANDS", "0F");
		commands.put("CMD_BOX_BREAK", "10");
		commands.put("CMD_NEWLINE", "11");
		commands.put("CMD_BOX_BREAK2", "12");
		commands.put("CMD_CARRIAGE_RETURN", "13");
		commands.put("CMD_SHIFT", "14");
		commands.put("CMD_CONTINUE", "15");
		commands.put("CMD_NAME", "16");
		commands.put("CMD_QUICKTEXT_ENABLE", "17");
		commands.put("CMD_QUICKTEXT_DISABLE", "18");
		commands.put("CMD_EVENT", "19");
		commands.put("CMD_PERSISTENT", "1A");
		commands.put("CMD_BOX_BREAK_DELAYED", "1B");
		commands.put("CMD_FADE", "1C");
		commands.put("CMD_FADE_SKIPPABLE", "1D");
		commands.put("CMD_SFX", "1E");
		commands.put("CMD_DELAY", "1F");
		commands.put("CMD_BTN_A", "B0");
		commands.put("CMD_BTN_B", "B1");
		commands.put("CMD_BTN_C", "B2");
		commands.put("CMD_BTN_L", "B3");
		commands.put("CMD_BTN_R", "B4");
		commands.put("CMD_BTN_Z", "B5");
		commands.put("CMD_BTN_CUP", "B6");
		commands.put("CMD_BTN_CDOWN", "B7");
		commands.put("CMD_BTN_CLEFT", "B8");
		commands.put("CMD_BTN_CRIGHT", "B9");
		commands.put("CMD_Z_TARGET", "BA");
		commands.put("CMD_CONTROL_PAD", "BB");
		commands.put("CMD_END", "BF");
		commands.put("CMD_BACKGROUND", "C1");
		commands.put("CMD_TWO_CHOICE", "C2");
		commands.put("CMD_THREE_CHOICE", "C3");
		commands.put("CMD_TIMER_POSTMAN", "C4");
		commands.put("CMD_TIMER_MINIGAME_1", "C5");
		commands.put("CMD_TIMER_2", "C6");
		commands.put("CMD_TIMER_MOON_CRASH", "C7");
		commands.put("CMD_TIMER_MINIGAME_2", "C8");
		commands.put("CMD_TIMER_ENV_HAZARD", "C9");
		commands.put("CMD_TIME", "CA");
		commands.put("CMD_CHEST_FLAGS", "CB");
		commands.put("CMD_INPUT_BANK", "CC");
		commands.put("CMD_RUPEES_SELECTED", "CD");
		commands.put("CMD_RUPEES_TOTAL", "CE");
		commands.put("CMD_TIME_UNTIL_MOON_CRASH", "CF");
		commands.put("CMD_INPUT_DOGGY_RACETRACK_BET", "D0");
		commands.put("CMD_INPUT_BOMBER_CODE", "D1");
		commands.put("CMD_PAUSE_MENU", "D2");
		commands.put("CMD_TIME_SPEED", "D3");
		commands.put("CMD_OWL_WARP", "D4");
		commands.put("CMD_INPUT_LOTTERY_CODE", "D5");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE", "D6");
		commands.put("CMD_STRAY_FAIRIES_LEFT_WOODFALL", "D7");
		commands.put("CMD_STRAY_FAIRIES_LEFT_SNOWHEAD", "D8");
		commands.put("CMD_STRAY_FAIRIES_LEFT_GREAT_BAY", "D9");
		commands.put("CMD_STRAY_FAIRIES_LEFT_STONE_TOWER", "DA");
		commands.put("CMD_POINTS_BOAT_ARCHERY", "DB");
		commands.put("CMD_LOTTERY_CODE", "DC");
		commands.put("CMD_LOTTERY_CODE_GUESS", "DD");
		commands.put("CMD_HELD_ITEM_PRICE", "DE");
		commands.put("CMD_BOMBER_CODE", "DF");
		commands.put("CMD_EVENT2", "E0");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_1", "E1");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_2", "E2");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_3", "E3");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_4", "E4");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_5", "E5");
		commands.put("CMD_SPIDER_HOUSE_MASK_CODE_6", "E6");
		commands.put("CMD_HOURS_UNTIL_MOON_CRASH", "E7");
		commands.put("CMD_TIME_UNTIL_NEW_DAY", "E8");
		commands.put("CMD_HS_POINTS_BANK_RUPEES", "F0");
		commands.put("CMD_HS_POINTS_UNK_1", "F1");
		commands.put("CMD_HS_POINTS_FISHING", "F2");
		commands.put("CMD_HS_TIME_BOAT_ARCHERY", "F3");
		commands.put("CMD_HS_TIME_HORSE_BACK_BALLOON", "F4");
		commands.put("CMD_HS_TIME_LOTTERY_GUESS", "F5");
		commands.put("CMD_HS_TOWN_SHOOTING_GALLERY", "F6");
		commands.put("CMD_HS_UNK_1", "F7");
		commands.put("CMD_HS_UNK_3_LOWER", "F8");
		commands.put("CMD_HS_HORSE_BACK_BALLOON", "F9");
		commands.put("CMD_HS_DEKU_PLAYGROUND_DAY_1", "FA");
		commands.put("CMD_HS_DEKU_PLAYGROUND_DAY_2", "FB");
		commands.put("CMD_HS_DEKU_PLAYGROUND_DAY_3", "FC");
		commands.put("CMD_DEKU_PLAYGROUND_NAME_DAY_1", "FD");
		commands.put("CMD_DEKU_PLAYGROUND_NAME_DAY_2", "FE");
		commands.put("CMD_DEKU_PLAYGROUND_NAME_DAY_3", "FF");
	}
}
