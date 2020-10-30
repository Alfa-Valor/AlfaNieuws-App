package com.alfa.alfanieuws.Helpers;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidatorHelper {

    public boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isValidName(String string){
        final String NAME_PATTERN = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }
}
