package TCP.Base;

class TCPBuffer extends TCPInfo{
    protected byte[] buffer;
    protected final int size = 8192;

    /** The set method for the buffer. */
    protected void setStreamBuffer(int size) {
        if(size>0)
            buffer = new byte[size];
        else
            buffer = new byte[this.size];
    }


    protected void clearBuffer() {
        for(int i=0;i<buffer.length;i++)
            buffer[i] = 0;
    }
}