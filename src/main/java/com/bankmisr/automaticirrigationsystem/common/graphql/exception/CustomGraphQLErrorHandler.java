package com.bankmisr.automaticirrigationsystem.common.graphql.exception;

import graphql.ErrorClassification;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomGraphQLErrorHandler implements graphql.kickstart.execution.error.GraphQLErrorHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        log.info("inside process errors");
        return errors.stream().map(this::getNested).collect(Collectors.toList());
    }

    private GraphQLError getNested(GraphQLError error) {
        log.info("inside get nested");
        GraphQLError e = null;
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            String errorMsg = exceptionError.getException().getMessage();
            if (exceptionError.getException() instanceof GraphQLError) {
                String enErrorMessage = messageSource.getMessage(errorMsg, null, new Locale("en"));
                String arErrorMessage = messageSource.getMessage(errorMsg, null, new Locale("ar"));

                Map<String, Object> localError = new HashMap<String, Object>();
                localError.put("errorEn", enErrorMessage);
                localError.put("errorAr", arErrorMessage);
                e = new GraphQLError() {

                    @Override
                    public String getMessage() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public List<SourceLocation> getLocations() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public ErrorClassification getErrorType() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Map<String, Object> getExtensions() {

                        return localError;// Collections.singletonMap("invalidField", localError);
                    }

                };
                return e;// (GraphQLError) exceptionError.getException();
            } else if (exceptionError.getException() instanceof NullPointerException) {
                errorMsg = "UNEXPECTED_ERROR";
                String enErrorMessage = messageSource.getMessage(errorMsg, null, new Locale("en"));
                String arErrorMessage = messageSource.getMessage(errorMsg, null, new Locale("ar"));

                Map<String, Object> localError = new HashMap<String, Object>();
                localError.put("errorEn", enErrorMessage);
                localError.put("errorAr", arErrorMessage);
                e = new GraphQLError() {

                    @Override
                    public String getMessage() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public List<SourceLocation> getLocations() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public ErrorClassification getErrorType() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public Map<String, Object> getExtensions() {

                        return localError;// Collections.singletonMap("invalidField", localError);
                    }

                };
                return e;// (GraphQLError) exceptionError.getException();
            }
        }

        return error;
    }

}