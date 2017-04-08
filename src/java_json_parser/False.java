/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_json_parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author noone
 */
public class False extends Value {

    byte[] myInternalBytes = new byte[4];

    public False(InputStream aStream) throws IOException {
        aStream.read(myInternalBytes);
    }

    public int GetWeight() {
        return 1;
    }

    @Override
    public void PrettyPrint(OutputStream aStream, int tabdepth) throws IOException {
        super.doTabDepth(tabdepth);
        aStream.write(myByteDepth);
        aStream.write(myInternalBytes);

    }

    @Override
    public java.lang.String toString() {
        return "false"; //TODO: check this value
    }
}
