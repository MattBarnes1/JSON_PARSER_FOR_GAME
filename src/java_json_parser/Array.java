/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_json_parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author noone
 */
public class Array extends Value {

    ArrayList<Value> myPair = new ArrayList<Value>();
    Byte StartValue = Convert.ToByte('[');
    Byte EndValue = Convert.ToByte(']');
    Byte[] newValueCommaOffset = {Convert.ToByte(','), Convert.ToByte('\n')};
    Byte[] newValueCommaNoOffset = {Convert.ToByte(','), Convert.ToByte(' ')};
    Byte newValueNoCommaOffset = Convert.ToByte('\n');
    Byte[] emptyArray = {Convert.ToByte('['), Convert.ToByte(' '), Convert.ToByte(']')};

    public int GetWeight() {
        int ReturnValue = 1;
        for(Value aVal : myPair)
        {
            ReturnValue += aVal.GetWeight();
        }
        return ReturnValue;
    }

    public void PrettyPrint(OutputStream aStream, int tabdepth) {
        base.doTabDepth(tabdepth);
        aStream.Write(base.myByteDepth, 0, base.myByteDepth.Length);
        if (myPair.Count == 0) {
            aStream.Write(emptyArray, 0, emptyArray.Length);
            return;
        }
        aStream.WriteByte(StartValue);
        aStream.WriteByte(newValueNoCommaOffset);

        for (int i = 0; i < myPair.Count; i++) {
            myPair[i].PrettyPrint(aStream, tabdepth + 1);
            if ((i + 1) == myPair.Count) {
                aStream.WriteByte(newValueNoCommaOffset);
            } else {
                aStream.Write(newValueCommaOffset, 0, newValueCommaNoOffset.Length);
            }
        }
        aStream.Write(base.myByteDepth, 0, base.myByteDepth.Length);
        aStream.WriteByte(EndValue);
    }

    public int getLength() {
        return myPair.Count;
    }

    private Value doValuePair(InputStream input) throws IOException
    {
        byte aCharacter = (byte)input.read();
           if (aCharacter == Byte.parseByte("\"")) {
                return new String(input);
            }
            else if (aCharacter == Byte.parseByte("[")) {
                return new Array(input); //Will break this function if all chars don't handle their closing items
            }
            else if (Character == Byte.parseByte("{")) {
                return new aObject(input);
            }
            else if (Character == Byte.parseByte("n")) {
                return new Null(input);
            }
            else if (Character == Byte.parseByte("f")) {
                return new False(input);
            }
            else if (Character == Byte.parseByte("t")) {
                return new True(input);
            }
            else if (Character.isDigit((char)Character))
            {
                return new Number(input);
            }
            else if (aString[aPosition].Equals(' ') || aString[aPosition].Equals('\r') || aString[aPosition].Equals('\n')) {
                    
                    return doValuePair(input);
            }
            else if (aString[aPosition].Equals(',')) {
                    
                    return doValuePair(aString, ref aPosition);
            }
            else if (aString[aPosition].Equals(']')) {
                    aPosition++;
                    return null;
                }
        Console.Write("Unhandled Character: " + aString[aPosition] + " at character position: " + aPosition + "Substring: " + aString.Substring(aPosition, 40));
        throw new Exception("Unhandled Character: " + aString[aPosition] + " at character position: " + aPosition);
    }

    public Array(InputStream input) throws IOException {
        byte readData = (byte)input.read();
        while (readData != Byte.parseByte("]")) {
            Value aVal = doValuePair(input);
            if (aVal == null) {
                break; //Used to prevent empty arrays from adding a bad char
            }
            myPair.add(aVal);
        }
        byte readData = (byte)input.read(); //Moves us out of the array so we don't have to bother with it.
    }
}
