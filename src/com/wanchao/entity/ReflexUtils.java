package com.wanchao.entity;

import com.wanchao.JavaClassLoader;
import com.wanchao.OrderService;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflexUtils {
    public static Object newInstance(Class classInfo) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.使用java反射技术拼接OrderService源代码
        StringBuffer sf=new StringBuffer();
        sf.append("package com.wanchao;");
        String simpleName = classInfo.getSimpleName()+"Impl";
        sf.append("public class "+simpleName
                +" implements "+classInfo.getSimpleName()+" { ");
        Method[] methods=classInfo.getMethods();
        for (Method method:methods){
            sf.append("public "+method.getReturnType().getSimpleName()
                    +" "+method.getName()+""+"(String ver1,String ver2){"
                    +"return  \"orderId为：1 orderName为：lingsheng\""+";}");
        }
        sf.append("}");
        //2.将java源代码写入到硬盘
        String fileName="d:/code/"+simpleName+".java";
        File f=new File(fileName);
        FileWriter fw=new FileWriter(f);
        fw.write(sf.toString());
        fw.flush();
        fw.close();
        //3.编译class文件
        JavaCompiler compiler= ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileMgr=compiler.getStandardFileManager(null,null,null);
        Iterable units=fileMgr.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask t= compiler.getTask(null,fileMgr,null,null,null,units);
        t.call();
        fileMgr.close();
        //4.使用类加载器将该class文件读取到内存中
        JavaClassLoader javaClassLoader=new JavaClassLoader();
        Class<?> aClass=javaClassLoader.findClass(simpleName);
        Object object=aClass.newInstance();
        return object;
    }
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException, IOException {
        /*Class userEntityClass = UserEntity.class;
        System.out.println(userEntityClass.getSimpleName());*/
        /*Class<?> classInfo=Class.forName("com.wanchao.entity.UserEntity");
        System.out.println(classInfo.getSimpleName());*/
        //需要先new出来
        /*Class<? extends UserEntity> aClass=new UserEntity().getClass();
        System.out.println(aClass.getSimpleName());*/
        //使用java的反射技术new对象
        //Class<?> classInfo=Class.forName("com.wanchao.entity.UserEntity");
        //实例化对象 无参构造函数
        //UserEntity userEntity= (UserEntity) classInfo.newInstance();
        /* userEntity.setUserName("lingsheng");
        System.out.println(userEntity.getUserName());*/
        /*Constructor<?> constructor=classInfo.getConstructor(Integer.class,String.class);
        UserEntity userEntity= (UserEntity) constructor.newInstance(123,"凌晟就是牛逼！");
        System.out.println("userId为："+userEntity.getUserId()+" userName为："+userEntity.getUserName());*/
        /*Field field=classInfo.getDeclaredField("userName");
        field.setAccessible(true);
        field.set(userEntity,"凌晟就是666");
        System.out.println(userEntity.getUserName());*/
        /*Method addUserMethod=classInfo.getDeclaredMethod("addUser", String.class);
        Object mayikt=addUserMethod.invoke(userEntity,"lingsheng");*/
        OrderService orderService= (OrderService) newInstance(OrderService.class);
        String s=orderService.addOrder("lingsheng","wanglili");
        System.out.println(s);
    }
}
