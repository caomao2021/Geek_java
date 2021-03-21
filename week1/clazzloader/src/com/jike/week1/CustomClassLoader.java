package com.jike.week1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends  ClassLoader {

    /**
     * 由参数path的构造方法
     *
     */
    private String path;
    private CustomClassLoader(String path){
        this.path=path;
    }
    /**
     * 将class文件转化为字节码数组
     * @return
     */
    private byte[] loadClasConvertByteArray(String path) {
        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }

    /**
     * 重写findClass方法
     * @param name 是我们这个类的全路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class hello = null;
        // 获取该class文件字节码数组
        byte[] classData =loadClasConvertByteArray(path);
        for (int i = 0; i <classData.length ; i++) {
            classData[i]= (byte) (255-classData[i]);
        }
        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            hello = defineClass(name, classData, 0, classData.length);
        }
        return hello;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String path="/Users/caomao/IdeaProjects/jike/clazzloader/src/com/jike/week1/Hello.xlass";
        CustomClassLoader customClassLoader=new CustomClassLoader(path);
        Class hello=customClassLoader.findClass("Hello");
        Method method = hello.getDeclaredMethod("hello");
        Object object = hello.newInstance();
        method.invoke(object);
    }

}
