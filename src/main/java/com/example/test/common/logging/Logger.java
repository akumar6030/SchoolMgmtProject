package com.example.test.common.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class Logger {
	
	@Around(value ="execution(* com.example.test.student.*.*(..))"
		)
	public Object logErsAppUi(ProceedingJoinPoint jointPoint) throws Throwable {
		Object obj = null;
		try {

			log.info("ENTERING_TO : " + jointPoint.getSignature().toShortString()+ " "); 

			long start = System.currentTimeMillis();

			obj = jointPoint.proceed();

			long elapsedTime = System.currentTimeMillis() - start;

			log.info("EXITING_FROM : " + jointPoint.getSignature().toShortString(),"with successfull execution.");
			log.info("Method execution time: " + elapsedTime + " milliseconds.", " ");

			return obj;
		} catch (Exception e) {
			log.error("EXCEPTION_CAME_ON : " + jointPoint.getSignature().toShortString()
					+  "FOR_INPUT : "
					+ Arrays.toString(jointPoint.getArgs()));
			throw e;
		}
	}

	public void logResponse(ResponseEntity<?> returnValue) {
		ObjectMapper mapper = new ObjectMapper();
		try {
				String jsonResponse = mapper.writeValueAsString(returnValue);
				log.info("RESPONSE : " + jsonResponse);
		} catch (JsonProcessingException e) {
			log.error("ERROR_MESSAGE = ", e);
		}
	}

}
