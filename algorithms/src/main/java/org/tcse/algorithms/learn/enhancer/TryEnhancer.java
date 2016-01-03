/**
 * @(#)Enhancer.java, Jan 03, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tcse.algorithms.learn.enhancer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.tcse.algorithms.learn.enhancer.thrift.Test.Client;
import org.tcse.algorithms.learn.enhancer.thrift.Test.Iface;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chentienan
 */
public class TryEnhancer {

    public void enhance (Iface iface, Handler handler) {
        Method[] methods = Iface.class.getDeclaredMethods();
        //Iface's public method is all the rpc methods
        List<Method> publicMethods = Arrays.stream(methods).filter(
                t -> Modifier.isPublic(t.getModifiers()) /*get public methods only*/
        ).collect(Collectors.toList());

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(iface.getClass());
        enhancer.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                    MethodProxy methodProxy) throws Throwable {
                if (!Modifier.isPublic(method.getModifiers()) || !publicMethods.contains(method)) {
                    return methodProxy.invokeSuper(obj, args);
                }
                handler.startAnalysis(method.getName());
                Object returnVal = null;
                try {
                    returnVal = method.invoke(obj, args);
                } finally {
                    handler.stopAnalysis(method.getName());
                }
                return returnVal;
            }
        });
    }

    @Test
    public void test() {

        Client client = new Client(new TJSONProtocol(new TTransport() {
            @Override
            public boolean isOpen() {
                return true;
            }

            @Override
            public void open() throws TTransportException {

            }

            @Override
            public void close() {

            }

            @Override
            public int read(byte[] bytes, int off, int limit) throws TTransportException {
                byte[] underRead= "[{hah:a}]".getBytes(StandardCharsets.US_ASCII);
                int readedBytes = 0;
                for (int i = off; i < off + limit && i < underRead.length; i++) {
                    readedBytes ++;
                    bytes[i] = underRead[i];
                }
                return readedBytes;
            }

            @Override
            public void write(byte[] bytes, int i, int i1) throws TTransportException {

            }
        }));
        new TryEnhancer().enhance(client, new Handler());
        try {
            client.get("hah");
        } catch (TException e) {
            e.printStackTrace();
        }

    }


    private static class Handler {

        private long startTime;
        private long endTime;

        public void startAnalysis(String methodName) {
            startTime = System.currentTimeMillis();
        }

        public void stopAnalysis(String methodName) {
            endTime = System.currentTimeMillis();
            System.out.println(methodName + " use time: " + (endTime - startTime));
        }


    }

}