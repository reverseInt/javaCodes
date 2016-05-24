// https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html
// implement add, minus and negate

import java.util.Arrays;

class BigInteger {
    private byte[] value;
    private boolean isPositive = true; 
    
    private boolean isZero() {
        return value.length == 1 && value[0] == 0;
    }
    
    public byte[] getValue() {
        return value;
    }
    
    public boolean isPositive() {
        return isPositive;
    }
    
    public BigInteger(byte[] value, boolean isPositive) {
        this.value = value;
        this.isPositive = isPositive;
    }
    
    public BigInteger(String str) {
        int n = str.length();
        if (n == 0) value = new byte[0];
        int i = 0;
        if (str.charAt(0) == '-') {
            isPositive = false;
            str = str.substring(1);
            ++i;
        } 
        value = new byte[n-i];
        int j = 0;
        for (char c : str.toCharArray()) {
            value[j++] = (byte)(c-'0');
        }
    }
    
    private static byte[] addTwoBigNumber(byte[] v1, byte[] v2) {
        int n1 = v1.length;
        int n2 = v2.length;
        int n = Math.max(n1, n2);
        byte[] res = new byte[n];
        byte carry = 0;
        for (int i = 0; i < n; ++i) {
            byte t1 = i < n1 ? v1[n1-1-i] : 0;
            byte t2 = i < n2 ? v2[n2-1-i] : 0;
            byte temp = (byte)(t1 + t2 + carry);
            res[n-1-i] = (byte)(temp % 10);
            carry = (byte)(temp/10);
        }
        if (carry > 0) {
            byte[] temp = new byte[n+1];
            temp[0] = 1;
            for (int i = 0; i < n; ++i) {
                temp[i+1] = res[i];
            }
            return temp;
        } else {
            return res;
        }
    }
    
    private static byte[] subtractTwoBigNumber(byte[] v1, byte[] v2) {
        // assume absolute value of v1 is no less than absolute value of v2
        int n1 = v1.length;
        int n2 = v2.length;
        byte borrow = 0;
        byte[] res = new byte[n1];
        for (int i = 0; i < n1; ++i) {
            byte t1 = (byte)(v1[n1-1-i] - borrow);
            byte t2 = (byte)(i < n2 ? v2[n2-1-i] : 0);
            if (t1 >= t2) {
                res[n1-1-i] = (byte)(t1-t2);
                borrow = 0;
            } else {
                res[n1-1-i] = (byte)(t1+10-t2);
                borrow = 1;
            }
        }
        int j = 0;
        for (; j < n1; ++j) {
            if (res[j] != 0) {
                break;
            }
        }
        return Arrays.copyOfRange(res, j, n1);
    }
    
    private static int compare(byte[] v1, byte[] v2) {
        if (v1.length != v2.length) return v1.length - v2.length;
        for (int i = 0; i < v1.length; ++i) {
            if (v1[i] != v2[i]) return v1[i] - v2[i];
        }
        return 0;
    }
    
    public BigInteger add(BigInteger b2) {
        byte[] v1 = getValue();
        byte[] v2 = b2.getValue();
        if (isPositive() && b2.isPositive()) {
            return new BigInteger(addTwoBigNumber(v1, v2), true);
        }
        if (! isPositive() && ! b2.isPositive()) {
            return new BigInteger(addTwoBigNumber(v1, v2), false);
        }
        if (compare(v1, v2) == 0) {
            return new BigInteger(new byte[]{0}, true);
        } else if (compare(v1, v2) > 0) {
            return new BigInteger(subtractTwoBigNumber(v1, v2), isPositive());
        } else {
            return new BigInteger(subtractTwoBigNumber(v2, v1), b2.isPositive());
        }
    }
    
    public BigInteger minus(BigInteger b2) {
        byte[] v1 = getValue();
        byte[] v2 = b2.getValue();
        if (isPositive() && ! b2.isPositive()) {
            return new BigInteger(addTwoBigNumber(v1, v2), true);
        }
        if (! isPositive() && b2.isPositive()) {
            return new BigInteger(addTwoBigNumber(v1, v2), false);
        }
        if (compare(v1, v2) == 0) {
            return new BigInteger(new byte[]{0}, true);
        } else if (compare(v1, v2) > 0) {
            return new BigInteger(subtractTwoBigNumber(v1, v2), isPositive());
        } else {
            return new BigInteger(subtractTwoBigNumber(v2, v1), ! b2.isPositive());
        }
    }

    public BigInteger negate() {
        if (isZero()) { 
            return new BigInteger(new byte[]{0}, true);
        } else {
            return new BigInteger(getValue(), ! isPositive());
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (! isPositive && ! isZero()) {
            sb.append("-");
        }
        for (byte b : value) {
            sb.append(b);
        }
        return sb.toString();
    }
    
    public static void main(String...args) {
        BigInteger b1 = new BigInteger("-1102020");
        BigInteger b2 = new BigInteger("-2912993");
        BigInteger b3 = b1.add(b2);
        BigInteger b4 = b1.minus(b2);
        System.out.println(b3);
        System.out.println(b4);
        
    }
}
