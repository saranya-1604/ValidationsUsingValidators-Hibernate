package com.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;

//import hb_demos.HibernateUtil;

//import org.hibernate.mapping.Set;

public class Uservalidations {
public static void main(String [] args) {
	ValidatorFactory validatorFactory=Validation.buildDefaultValidatorFactory();
	Validator validator=validatorFactory.getValidator();
	System.out.println("checking for invalid user data....");
	System.out.println("------------------------------------");
	Users invalidUser =new Users(null,"a","test123",12456,"Javatechnology","db","","1234","y",-2,1,new Date(),getPastOrFutureDate(-2),
			getPastOrFutureDate(-2),5,"sample1.com","123@");
	Set<ConstraintViolation<Users>> violations=validator.validate(invalidUser);
	if(violations.isEmpty()) {
		System.out.println("Valid user data provided");
		Transaction tr = null;
		try (Session session = HBUtil.getSesFactory().openSession()) {
		    tr = session.beginTransaction();
		    session.persist(invalidUser);
		    
		    tr.commit();
		} catch (   Exception x) {
		    if (tr != null) {
		        tr.rollback();
		    }
		    x.printStackTrace();
		}
	}
	else {
		System.out.println("Invalid user data found");
		for(ConstraintViolation<Users> violation: violations) {
			System.out.println(violation.getMessage());
		}
	}
	System.out.println("Checking for valid user data...........");
	Users validUser=new Users(1L,"author","a@gamil.com",16,"4","3","ML",null,"YN",2,0,getPastOrFutureDate(2),getPastOrFutureDate(1),getPastOrFutureDate(-2),2,"https://www.vsb.org/","9785421467867453");
	violations=validator.validate(validUser);
	if(violations.isEmpty()) {
		System.out.println("Valid user data provided");
		Transaction tr = null;
		try (Session session = HBUtil.getSesFactory().openSession()) {
		    tr = session.beginTransaction();
		    session.persist(validUser);
		    
		    tr.commit();
		} catch (   Exception x) {
		    if (tr != null) {
		        tr.rollback();
		    }
		    x.printStackTrace();
		}
	}
	else {
		System.out.println("Invalid user data found");
		for(ConstraintViolation<Users> violation: violations) {
			System.out.println(violation.getMessage());
		}
	}
	System.out.println("-------------------------------------");
}
public static Date getPastOrFutureDate(int days) {
	Calendar calendar=Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.DATE,days);
	return calendar.getTime();
}

}
