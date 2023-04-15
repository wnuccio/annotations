package annotations.processor;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {
    private Class<? extends Annotation> annotationClass;
    private String packageName;

    public Processor(String packageName, Class<MyAnnotation> annotationClass) {
        this.packageName = packageName;
        this.annotationClass = annotationClass;
    }

    private boolean isAnnotated(Class<?> aClass) {
        return aClass.isAnnotationPresent(annotationClass);
    }

    public <T> Optional<T> createAnnotatedClassIfExists(Class<T> superType) {
        return createAnnotatedClasses(superType)
                .stream()
                .findFirst();
    }

    public <T> List<T> createAnnotatedClasses(Class<T> superType) {
        return findAnnotatedClasses()
                .stream()
                .filter(superType::isAssignableFrom)
                .map(aClass -> instantiate(aClass, superType))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("UnstableApiUsage")
    private Set<Class<?>> findAllClassesInPackage(String packageName) {
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

    public Set<Class<?>> findAnnotatedClasses() {
        return findAllClassesInPackage(packageName)
                .stream()
                .filter(this::isAnnotated)
                .collect(Collectors.toSet());
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
}
