package core;

import java.nio.ByteBuffer;

/**
 * Classe representante da estrutura de dados utilizada para
 * fazer as operações com o servidor.
 * 
 * @author daniel e mara
 */
public class Packet {
    private int mode;
    private int verb;
    private int finished;
    private int formatData;
    private String resource = "";
    private String params = "";
    private byte[] data = new byte[0];

    public Packet mode(int m) {
        mode = m;
        return this;
    }
    public Packet verb(int v) {
        verb = v;
        return this;
    }
    public Packet finished(int f) {
        finished = f;
        return this;
    }
    public Packet formatData(int f) {
        formatData = f;
        return this;
    }
    public Packet resource(String r) {
        resource = r;
        return this;
    }
    public Packet params(String p) {
        params = p;
        return this;
    }
    public Packet data(byte[] d) {
        data = d;
        return this;
    }
    private Packet resource(ByteBuffer b) {
        byte[] r = new byte[b.get()];
        b.get(r);
        resource = new String(r);
        return this;
    }
    private Packet params(ByteBuffer b) {
        byte[] p = new byte[b.get()];
        b.get(p);
        params = new String(p);
        return this;
    }
    private Packet data(ByteBuffer b) {
        byte[] d = new byte[b.getShort()];
        b.get(d);
        data = d;
        return this;
    }

    public int mode() {
        return mode;
    }
    public int verb() {
        return verb;
    }
    public int finished() {
        return finished;
    }
    public int formatData() {
        return formatData;
    }
    public String resource() {
        return resource;
    }
    public String params() {
        return params;
    }
    public byte[] data() {
        return data;
    }
    
    public int length() {
        return 8 + this.resource.length() + this.params.length() + this.data.length;
    }

    public byte[] getBytes() {
        return ByteBuffer.allocate(this.length())
            .put((byte) mode)
            .put((byte) verb)
            .put((byte) finished)
            .put((byte) formatData)
            .put((byte) resource.length())
            .put(resource.getBytes())
            .put((byte) params.length())
            .put(params.getBytes())
            .putShort((byte) data.length)
            .put(data)
            .array();
     }

    public static Packet fromBytes(byte[] buffer) {
        ByteBuffer b = ByteBuffer.wrap(buffer).asReadOnlyBuffer();
        return new Packet()
            .mode(b.get())
            .verb(b.get())
            .finished(b.get())
            .formatData(b.get())
            .resource(b)
            .params(b)
            .data(b);
    }

    public String toString() {
        return mode + "|" + verb + "|" + resource + "|" + params + "|" 
            + finished + "|" + formatData + "|" + data.length;
    }
}
