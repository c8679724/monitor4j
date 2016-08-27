package com.sky.profiler4j.test.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.sky.profiler4j.aspect.asm.CustomClassVisitor;

public class Test_App3 extends ClassLoader {

	public static void main(String[] args) throws Exception {

		Class<?> clazz = App3.class;

		ClassReader classReader = new ClassReader(clazz.getName());
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		CustomClassVisitor customClassVisitor = new CustomClassVisitor(Opcodes.ASM5, classWriter, clazz.getName());
		classReader.accept(customClassVisitor, ClassReader.EXPAND_FRAMES);

		byte[] code = classWriter.toByteArray();

		// method.invoke new App3().test();

		Test_App3 loader = new Test_App3();
		Class<?> testClass = (Class<App3>) loader.defineClass(null, code, 0, code.length);
		testClass.getMethods()[1].invoke(testClass.newInstance(), new Object[] {});
	}
}