/**
 *  Copyright (C) 2008-2017  Telosys project org. ( http://www.telosys.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.telosys.tools.generic.model.types;

/**
 * Literal values provider for "JAVA" language
 * 
 * @author Laurent GUERIN
 *
 */
public class LiteralValuesProviderForJava extends LiteralValuesProvider {
	
	private static final String NULL_LITERAL  = "null" ; // null in Java
	private static final String TRUE_LITERAL  = "true" ; 
	private static final String FALSE_LITERAL = "false" ; 
	
	@Override
	public String getLiteralNull() {
		return NULL_LITERAL;
	}
	
	@Override
	public String getLiteralTrue() {
		return TRUE_LITERAL;
	}

	@Override
	public String getLiteralFalse() {
		return FALSE_LITERAL;
	}
			
//	private int checkThreshold(int step, int max) {
//		if ( step <= max ) {
//			// OK ( 0 to MAX )
//			return step ;
//		}
//		else {
//			int modulo = step % max ;
//			if ( modulo == 0 ) {
//				return max ;
//			}
//			else {
//				return modulo ;
//			}
//		}
//		
//	}
	
	@Override
	public String generateLiteralValue(LanguageType languageType, int maxLength, int step) {
		
		String javaFullType = languageType.getFullType() ;
		//--- STRING
		if ( java.lang.String.class.getCanonicalName().equals(javaFullType) ) {
			return "\"" + buildStringValue(maxLength, step) + "\"" ;
		}
		
		//--- BYTE
		else if ( byte.class.getCanonicalName().equals(javaFullType) ) {
			return "(byte)" + checkThreshold(step, Byte.MAX_VALUE) ;
		}
		else if ( java.lang.Byte.class.getCanonicalName().equals(javaFullType) ) {
			//Byte.valueOf(12); // Invalid
			//Byte.valueOf((byte)12); // OK
			return "Byte.valueOf((byte)" + checkThreshold(step, Byte.MAX_VALUE) + ")" ;
		}
		
		//--- SHORT
		else if ( short.class.getCanonicalName().equals(javaFullType) ) {
			return "(short)" + checkThreshold(step, Short.MAX_VALUE) ;
		}
		else if ( java.lang.Short.class.getCanonicalName().equals(javaFullType) ) {
			//Short.valueOf(12); // Invalid
			//Short.valueOf((short)12); // OK
			return "Short.valueOf((short)" + checkThreshold(step, Short.MAX_VALUE) + ")" ;
		}
		
		//--- INT		
		else if ( int.class.getCanonicalName().equals(javaFullType) ) {
			return "" + (step*100);
		}
		else if ( java.lang.Integer.class.getCanonicalName().equals(javaFullType) ) {
			Integer.valueOf(12);
			return "Integer.valueOf(" + (step*100) + ")";
		}
		
		//--- LONG
		else if ( long.class.getCanonicalName().equals(javaFullType) ) {
			return (step*1000) + "L" ;
		}
		else if ( java.lang.Long.class.getCanonicalName().equals(javaFullType) ) {
			// Long.valueOf(12L);
			return "Long.valueOf(" + (step*1000) + "L)" ;
		}
		
		//--- FLOAT
		else if ( float.class.getCanonicalName().equals(javaFullType) ) {
			return (step*1000) + ".5F" ;
		}
		else if ( java.lang.Float.class.getCanonicalName().equals(javaFullType) ) {
			// Float.valueOf(12.5F);
			return "Float.valueOf(" + (step*1000) + ".5F)" ;
		}
		
		//--- DOUBLE
		else if ( double.class.getCanonicalName().equals(javaFullType) ) {
			return (step*1000) + ".66D" ;
		}
		else if ( java.lang.Double.class.getCanonicalName().equals(javaFullType) ) {
			//Double.valueOf(12.66D);
			return "Double.valueOf(" + (step*1000) + ".66D)" ;
		}
		
		//--- BIG DECIMAL
		else if ( java.math.BigDecimal.class.getCanonicalName().equals(javaFullType) ) {
			// java.math.BigDecimal.valueOf(15000);
			return "java.math.BigDecimal.valueOf(" + (step * 10000) + ")" ;
		}
		
		//--- BOOLEAN
		else if ( boolean.class.getCanonicalName().equals(javaFullType) ) {
			// Primitive boolean type
//			Boolean val = step % 2 != 0 ? Boolean.TRUE : Boolean.FALSE ;
//			return val.toString() ;
			return step % 2 != 0 ? TRUE_LITERAL : FALSE_LITERAL ;

		}
		else if ( java.lang.Boolean.class.getCanonicalName().equals(javaFullType) ) {
//			// Boolean.valueOf(true);
//			Boolean val = step % 2 != 0 ? Boolean.TRUE : Boolean.FALSE ;
//			return "Boolean.valueOf(" + val.toString() + ")";
			// Object boolean wrapper type
			return "Boolean.valueOf(" + (step % 2 != 0 ? TRUE_LITERAL : FALSE_LITERAL) + ")";
		}
		
		// DATE & TIME
		else if ( java.util.Date.class.getCanonicalName().equals(javaFullType) ) {
			String neutralType = languageType.getNeutralType();
			// A standard "java.util.Date" can be used to store Date/Time/Timestamp
			if ( NeutralType.DATE.equals(neutralType) ) {
				return generateDateValue(step);
			}
			else if ( NeutralType.TIME.equals(neutralType) ) {
				return generateTimeValue(step);
			}
			else if ( NeutralType.TIMESTAMP.equals(neutralType) ) {
				return generateTimestampValue(step);
			}
			else {
				// by default generate a DATE
				return generateDateValue(step);
			}
		}
		else if ( java.sql.Date.class.getCanonicalName().equals(javaFullType) ) {
//			String dateISO = (2000+step) + "-06-22" ; // "2001-06-22" 
//			return "java.sql.Date.valueOf(\"" + dateISO + "\")" ; 
			return generateDateValue(step);
		}
		else if ( java.sql.Time.class.getCanonicalName().equals(javaFullType) ) {
//			String timeISO =  String.format("%02d", (step%24) ) + ":46:52" ; // "15:46:52"
//			return "java.sql.Time.valueOf(\"" + timeISO + "\")" ; // "15:46:52"
			return generateTimeValue(step);
		}
		else if ( java.sql.Timestamp.class.getCanonicalName().equals(javaFullType) ) {
//			String timestampISO = (2000+step) + "-05-21" + " " + String.format("%02d", (step%24) ) + ":46:52" ; // "2001-05-21 15:46:52" 
//			return "java.sql.Timestamp.valueOf(\"" + timestampISO + "\")" ; // e.g. "2001-05-21 15:46:52"
			return generateTimestampValue(step);
		}
		
		return "null" ; 
	}
	
	private String generateYearValue(int step) {
		int year = 2000 + ( step % 1000 ) ;  // between 2000 and 2999 
		return "" + year ;
	}
	private String generateDateValue(int step) {
		String dateISO = generateYearValue(step) + "-06-22" ; // "2001-06-22" 
		return "java.sql.Date.valueOf(\"" + dateISO + "\")" ; 
	}
	private String generateTimeValue(int step) {
		String timeISO =  String.format("%02d", (step%24) ) + ":46:52" ; // "15:46:52"
		return "java.sql.Time.valueOf(\"" + timeISO + "\")" ; // "15:46:52"
	}
	private String generateTimestampValue(int step) {
		String timestampISO = generateYearValue(step) + "-05-21" + " " + String.format("%02d", (step%24) ) + ":46:52" ; // "2001-05-21 15:46:52" 
		return "java.sql.Timestamp.valueOf(\"" + timestampISO + "\")" ; // e.g. "2001-05-21 15:46:52"
	}

	/* 
	 * Returns something like that : 
	 *   ' == 100' 
	 *   '.equals("xxx")'
	 */
	@Override
	public String getEqualsStatement(String value, LanguageType languageType) {
		
		if ( languageType.isPrimitiveType() ) {
			// equals for primitive type
			StringBuilder sb = new StringBuilder();
			sb.append( " == " );
			sb.append( value );
			//sb.append( " " );
			return sb.toString();
		}
		else {
			// equals for object instance
			StringBuilder sb = new StringBuilder();
			sb.append( ".equals(" );
			sb.append( value );
			sb.append( ")" );
			return sb.toString();

		}
	}

//	private String buildStringValue(int maxLength, int step) {
//		int maxLimit = 100 ;
//		// 'A'-'Z' : 65-90 
//		// 'a'-'z' : 97-122 
//		char c = 'A' ; 
//		if ( step > 0 ) {
//			int delta = (step-1) % 26;
//			c = (char)('A' + delta );
//		}
//		StringBuilder sb = new StringBuilder();
//		for ( int i = 0 ; i < maxLength && i < maxLimit ; i++) {
//			sb.append(c);
//		}
//		return sb.toString();
//	}

}
