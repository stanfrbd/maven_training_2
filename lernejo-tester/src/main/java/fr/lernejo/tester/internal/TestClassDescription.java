package fr.lernejo.tester.internal;

import fr.lernejo.tester.api.TestMethod;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class TestClassDescription {
    Class<?> classObj;

    public TestClassDescription(Class<?> classArg) {
        this.classObj = classArg;
    }
    public List<Method> listTestMethods(){
        Method[] methods = classObj.getDeclaredMethods();
        List<Method> allMethods = new ArrayList();
        for (Method m: methods) {
            if(m.getReturnType().equals(Void.TYPE)
            && Modifier.isPublic(m.getModifiers())
            && m.getParameterTypes().length == 0
            && m.isAnnotationPresent(TestMethod.class)) {
                allMethods.add(m);
            }
        }
        return allMethods;
    }
}
