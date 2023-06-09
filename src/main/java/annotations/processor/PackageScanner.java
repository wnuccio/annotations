package annotations.processor;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageScanner {
    private Class<? extends Annotation> annotationClass;
    private String packageName;

    public PackageScanner(String packageName, Class<MyAnnotation> annotationClass) {
        this.packageName = packageName;
        this.annotationClass = annotationClass;
    }

    public Set<?> createAnnotatedClasses() {
        return createAnnotatedClasses(Object.class);
    }

    public <T> Set<T> createAnnotatedClasses(Class<T> superType) {
        return findAnnotatedClasses()
                .stream()
                .filter(superType::isAssignableFrom)
                .map(aClass -> instantiate(aClass, superType))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("UnstableApiUsage")
    public Set<Class<?>> findAnnotatedClasses() {
        return classPath()
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName))
                .map(ClassPath.ClassInfo::load)
                .filter(this::isAnnotated)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("UnstableApiUsage")
    private ClassPath classPath() {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T instantiate(Class<?> concreteClass, Class<T> superType) {
        try {
            return superType.cast(concreteClass.getDeclaredConstructor().newInstance());

        } catch (IllegalAccessException
                | NoSuchMethodException
                | InstantiationException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAnnotated(Class<?> aClass) {
        return aClass.isAnnotationPresent(annotationClass);
    }
}
