package org.fai.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Framework Annotation is user built annotation which is annotated on top of test methods to log the author details and 
 * category details to the extent report.<p>
 * 
 * Runtime retention value indicate that this annotation will be available at run time for reflection operations.
 * 
 *
 * 
 * Jan 21, 2021 
 * @author Ravichandra
 * */


@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface FrameworkAnnotation {
	/**
	 * Store the authors who created the tests in String[]
	 * Manadatory to enter atleast a value
	 * @author Ravi
	 * 
	 */
	public String[] author();
	
	/**
	 * Stores the category in form of Enum Array.
	 * @author Ravi
	 * Jan 21, 2021
	 */
	

}
