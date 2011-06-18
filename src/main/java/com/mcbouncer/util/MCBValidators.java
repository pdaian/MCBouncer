package com.mcbouncer.util;

public class MCBValidators {

    public static boolean UserAndReasonValidator(String[] args) {
        return args.length > 0;
    }

    public static boolean UserValidator(String[] args) {
        return args.length == 1;
    }
    
}
