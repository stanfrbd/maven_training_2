package fr.lernejo.tester.internal;

import fr.lernejo.tester.api.TestMethod;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestClassDiscoverer {
    final private String packageName;

    public TestClassDiscoverer(String packageArg) {
        this.packageName = packageArg;
    }

    public List<TestClassDescription> listTestClasses() {
        Reflections reflections = new Reflections(this.packageName, new SubTypesScanner(false));
        Set<Class<?>> allTypes = reflections.getSubTypesOf(Object.class);
        List<TestClassDescription> allDescription = new ArrayList();
        for (Class<?> c : allTypes) {
            boolean hasTestMethod = false;
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(TestMethod.class)) {
                    hasTestMethod = true;
                    break;
                }
            }
            if (hasTestMethod && c.getSimpleName().endsWith("LernejoTests")) {
                allDescription.add(new TestClassDescription(c));
            }
        }
        return allDescription;
    }
}
