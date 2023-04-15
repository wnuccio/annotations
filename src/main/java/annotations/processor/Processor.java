package annotations.processor;

import annotations.Annotation;
import annotations.classes.AnnotatedClass;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {
    public Set<Class<?>> getAnnotatedClasses() {
        return findAllClassesIn("annotations.classes")
                .stream().filter(clazz -> clazz.isAnnotationPresent(Annotation.class))
                .collect(Collectors.toSet());
    }

    public <T> T createAnnotatedClass(Class<T> aClass) {
        Set<Class<?>> classes = getAnnotatedClasses();
        Class<?> clazz = classes.iterator().next();
        try {
            return (aClass.cast(clazz.getDeclaredConstructor().newInstance()));

        } catch (IllegalAccessException
                | NoSuchMethodException
                | InstantiationException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public Set<Class<?>> findAllClassesIn(String packageName) {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAnnotated(Class<AnnotatedClass> aClass) {
        return aClass.isAnnotationPresent(Annotation.class);
    }
}
