package processor;

import annotations.classes.AnnotatedClass;
import annotations.classes.AnnotatedClass2;
import annotations.classes.AnnotatedClassWithDifferentBase;
import annotations.classes.BaseType;
import annotations.processor.MyAnnotation;
import annotations.processor.Processor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorTest {

    @Test
    void processor_returns_only_annotated_classes() {
        Processor processor = new Processor("annotations.classes", MyAnnotation.class);

        Set<Class<?>> annotatedClasses = processor.findAnnotatedClasses();

        assertEquals(3, annotatedClasses.size());
        assertTrue(annotatedClasses.contains(AnnotatedClass.class));
        assertTrue(annotatedClasses.contains(AnnotatedClass2.class));
        assertTrue(annotatedClasses.contains(AnnotatedClassWithDifferentBase.class));
    }

    @Test
    void processor_returns_an_instance_of_all_annotated_class_with_common_base() {
        Processor processor = new Processor("annotations.classes", MyAnnotation.class);

        List<BaseType> annotatedClass = processor.createAnnotatedClasses(BaseType.class);

        assertEquals(2, annotatedClass.size());
    }
}