package processor;

import annotations.classes.AnnotatedClass;
import annotations.classes.AnnotatedClass2;
import annotations.classes.AnnotatedClassWithDifferentBase;
import annotations.classes.BaseType;
import annotations.processor.MyAnnotation;
import annotations.processor.PackageScanner;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageScannerTest {

    @Test
    void scanner_returns_only_annotated_classes() {
        PackageScanner packageScanner = new PackageScanner("annotations.classes", MyAnnotation.class);

        Set<Class<?>> annotatedClasses = packageScanner.findAnnotatedClasses();

        assertEquals(3, annotatedClasses.size());
        assertTrue(annotatedClasses.contains(AnnotatedClass.class));
        assertTrue(annotatedClasses.contains(AnnotatedClass2.class));
        assertTrue(annotatedClasses.contains(AnnotatedClassWithDifferentBase.class));
    }

    @Test
    void scanner_returns_an_instance_of_all_annotated_class() {
        PackageScanner packageScanner = new PackageScanner("annotations.classes", MyAnnotation.class);

        Set<?> annotatedClass = packageScanner.createAnnotatedClasses();

        assertEquals(3, annotatedClass.size());
    }

    @Test
    void scanner_returns_an_instance_of_all_annotated_class_with_common_base() {
        PackageScanner packageScanner = new PackageScanner("annotations.classes", MyAnnotation.class);

        Set<BaseType> annotatedClass = packageScanner.createAnnotatedClasses(BaseType.class);

        assertEquals(2, annotatedClass.size());
    }
}