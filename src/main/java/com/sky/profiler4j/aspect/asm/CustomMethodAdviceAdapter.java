package com.sky.profiler4j.aspect.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class CustomMethodAdviceAdapter extends AdviceAdapter {

	private int method_id;

	public CustomMethodAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc, int method_id) {
		super(api, mv, access, name, desc);
		this.method_id = method_id;
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		super.visitLineNumber(line, start);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
	}

	@Override
	public void visitLabel(Label label) {
		super.visitLabel(label);
	}

	@Override
	protected void onMethodEnter() {
		// super.visitCode();
		mv.visitLdcInsn(new Integer(method_id));
		mv.visitMethodInsn(INVOKESTATIC, "com/sky/profiler4j/aspect/aop/ThreadProfiler", "enterMethod", "(I)V", false);
	}

	@Override
	protected void onMethodExit(int opcode) {

		mv.visitLdcInsn(new Integer(method_id));
		mv.visitMethodInsn(INVOKESTATIC, "com/sky/profiler4j/aspect/aop/ThreadProfiler", "exitMethod", "(I)V", false);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		if (maxStack > 1) {
			super.visitMaxs(maxStack, maxLocals + 3);
		} else {
			super.visitMaxs(maxStack + 1, maxLocals + 3);
		}
	}
}
