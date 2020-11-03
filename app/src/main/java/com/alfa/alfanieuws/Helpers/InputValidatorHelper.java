package com.alfa.alfanieuws.Helpers;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidatorHelper {

    // check if the name is valid based on the patterns. A name could not be valid when it holds numbers. Returns true or false.
    public boolean isValidName(String string){
        final String NAME_PATTERN = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    // check if it's null or empty and return true or false
    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }
}
