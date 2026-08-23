package com.code.util;

public class Contants {
 
	public static final String EMAIL_REGEX="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

	public static final String MOBNO_REGEX="^(?:\\+?\\d{1,3}[- ]?)?(?!0+$)\\d{10}$";
	
	public static final String ROLE_ADMIN="hasRole('ADMIN')";
	
	public static final String ROLE_USER="hasRole('USER')";
	
	public static final String ROLE_USER_ADMIN="hasAnyRole('ADMIN','USER')";
	
	public static final String PAGE_NO="0";

	public static final String PAGE_SIZE="10";
	
}
