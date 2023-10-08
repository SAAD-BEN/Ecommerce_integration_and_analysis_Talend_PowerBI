// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package routines.system;

import java.io.IOException;
import java.io.Writer;

/**
 * @(#)BufferedWriter.java 1.26 03/12/19
 *
 * @see java.io.BuffferedWriter
 * @author xzhang
 */

public class BufferedOutput extends java.io.Writer {

    private Writer out;

    private char cb[];

    private int nChars, nextChar;

    private static int defaultCharBufferSize = 8192;

    /**
     * Line separator string. This is the value of the line.separator property at the moment that the stream was
     * created.
     */
    private String lineSeparator;

    /**
     * Create a buffered character-output stream that uses a default-sized output buffer.
     *
     * @param out A Writer
     */
    public BufferedOutput(Writer out) {
        this(out, defaultCharBufferSize);
    }

    /**
     * Create a new buffered character-output stream that uses an output buffer of the given size.
     *
     * @param out A Writer
     * @param sz Output-buffer size, a positive integer
     *
     * @exception IllegalArgumentException If sz is <= 0
     */
    public BufferedOutput(Writer out, int sz) {
        super(out);
        if (sz <= 0)
            throw new IllegalArgumentException("Buffer size <= 0"); //$NON-NLS-1$
        this.out = out;
        cb = new char[sz];
        nChars = sz;
        nextChar = 0;

        lineSeparator = java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<String>() {

              @Override
              public String run() {
                return System.getProperty("line.separator"); //$NON-NLS-1$
              }

            }
        );
    }

    /** Check to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed"); //$NON-NLS-1$
    }

    /**
     * Flush the output buffer to the underlying character stream, without flushing the stream itself. This method is
     * non-private only so that it may be invoked by PrintStream.
     */
    void flushBuffer() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar == 0)
                return;
            out.write(cb, 0, nextChar);

            // Flush the buffer of inside writer.
            out.flush();
            nextChar = 0;
        }
    }

    /**
     * Write a single character.
     *
     * @exception IOException If an I/O error occurs
     */
    public void write(int c) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar >= nChars)
                flushBuffer();
            cb[nextChar++] = (char) c;
        }
    }

    /**
     * Our own little min method, to avoid loading java.lang.Math if we've run out of file descriptors and we're trying
     * to print a stack trace.
     */
    private int min(int a, int b) {
        if (a < b)
            return a;
        return b;
    }

    /**
     * Write a portion of an array of characters.
     *
     * <p>
     * Ordinarily this method stores characters from the given array into this stream's buffer, flushing the buffer to
     * the underlying stream as needed. If the requested length is at least as large as the buffer, however, then this
     * method will flush the buffer and write the characters directly to the underlying stream. Thus redundant
     * <code>BufferedWriter</code>s will not copy data unnecessarily.
     *
     * @param cbuf A character array
     * @param off Offset from which to start reading characters
     * @param len Number of characters to write
     *
     * @exception IOException If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }

            if (len >= nChars) {
                /*
                 * If the request length exceeds the size of the output buffer, flush the buffer and then write the data
                 * directly. In this way buffered streams will cascade harmlessly.
                 */
                flushBuffer();
                out.write(cbuf, off, len);
                return;
            }

            int b = off, t = off + len;
            while (b < t) {
                int d = min(nChars - nextChar, t - b);
                System.arraycopy(cbuf, b, cb, nextChar, d);
                b += d;
                nextChar += d;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }

    /**
     * Write a portion of a String.
     *
     * <p>
     * If the value of the <tt>len</tt> parameter is negative then no characters are written. This is contrary to the
     * specification of this method in the {@linkplain java.io.Writer#write(java.lang.String,int,int) superclass},
     * which requires that an {@link IndexOutOfBoundsException} be thrown.
     *
     * @param s String to be written
     * @param off Offset from which to start reading characters
     * @param len Number of characters to be written
     *
     * @user xzhang
     *
     * @exception IOException If an I/O error occurs
     */
    public void write(String s, int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();

            if (len >= nChars) {
                /*
                 * If the request length exceeds the size of the output buffer, flush the buffer and then write the data
                 * directly. In this way buffered streams will cascade harmlessly.
                 */
                flushBuffer();
                char[] cbuf = new char[len];
                s.getChars(off, (off + len), cbuf, 0);
                write(cbuf, 0, len);
                return;
            }

            if (len > 0) {
                /*
                 * If it doesn't write all char of string to buffer, flush buffer chars then put the String to buffer.
                 * Make sure String doesn't cut by ohter writer.
                 */
                if (nChars - nextChar < len) {
                    flushBuffer();
                }
                s.getChars(off, off + len, cb, nextChar);
                nextChar += len;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }

    /**
     * Write a line separator. The line separator string is defined by the system property <tt>line.separator</tt>,
     * and is not necessarily a single newline ('\n') character.
     *
     * @exception IOException If an I/O error occurs
     */
    public void newLine() throws IOException {
        write(lineSeparator);
    }

    /**
     * Flush the stream.
     *
     * @exception IOException If an I/O error occurs
     */
    public void flush() throws IOException {
        synchronized (lock) {
            flushBuffer();
            out.flush();
        }
    }

    /**
     * Close the stream.
     *
     * @exception IOException If an I/O error occurs
     */
    public void close() throws IOException {
        synchronized (lock) {
            if (out == null)
                return;
            flushBuffer();
            out.close();
            out = null;
            cb = null;
        }
    }

}
