package com.piple.res;

import java.security.SecureRandom;
import java.math.BigInteger;
/**
 * Created by jeremie on 09/03/2017.
 */

public final class KeyCreator {

        private SecureRandom random = new SecureRandom();

        public String Id() {
            return new BigInteger(130, random).toString(32);
        }
    }

