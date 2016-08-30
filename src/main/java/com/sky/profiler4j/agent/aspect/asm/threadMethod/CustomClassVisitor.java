package com.sky.profiler4j.agent.aspect.asm.threadMethod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.sky.profiler4j.agent.profile.util.MethodUtil;

public class CustomClassVisitor extends ClassVisitor implements Opcodes {

	/**
	 * 是否是接口类
	 */
	private boolean isInterface;

	private String className;

	public CustomClassVisitor(int api, ClassVisitor cv, String className) {
		super(api, cv);
		this.className = className;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		isInterface = (access & ACC_INTERFACE) != 0;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

		/**
		 * 是否是抽象方法
		 */
		boolean is_abstract;
		// 判断是否是抽象方法
		if ((access & Opcodes.ACC_ABSTRACT) == 0) {
			is_abstract = false;
		} else {
			is_abstract = true;
		}

		// 判断这个方法是否达到添加切面链的条件
		// .
		// .
		// .
		// .

		if (!is_abstract && !isInterface && mv != null && (!"<clinit>".equals(name))) {

			try {
				int method_id = MethodUtil.registerMethod(className + "." + name, desc);
				mv = new CustomMethodAdviceAdapter(Opcodes.ASM5, mv, access, name, desc, method_id);
			} catch (Exception e) {

			}

		}
		return mv;
	}
}
