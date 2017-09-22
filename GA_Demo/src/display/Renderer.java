package display;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackMallocFloat;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

public class Renderer {

	private ShaderProgram shaderProgram;

	private int VAO;
	private int modelMatrixLocation;
	private int projMatrixLocation;
	private int colorLocation;

	private Matrix4f model;
	private Matrix4f proj;

	public Renderer(int width, int height) {
		init(width, height);
	}

	public void init(int width, int height) {
		try {
			shaderProgram = new ShaderProgram();
			shaderProgram.createVertexShader("/Shaders/VertexShader.glsl");
			shaderProgram.createFragmentShader("/Shaders/FragmentShader.glsl");
			shaderProgram.link();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		proj = new Matrix4f();

		projMatrixLocation = glGetUniformLocation(shaderProgram.getProgramId(), "projectionMatrix");
		modelMatrixLocation = glGetUniformLocation(shaderProgram.getProgramId(), "modelMatrix");
		colorLocation = glGetUniformLocation(shaderProgram.getProgramId(), "color");

		createVAO();

		this.model = new Matrix4f();

		resize(width, height);
	}

	private void createVAO() {

		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);

		try (MemoryStack stack = stackPush()) {

			FloatBuffer vbuffer = stackMallocFloat(4 * 2);
			vbuffer.put(0.0f).put(0.0f);
			vbuffer.put(1.0f).put(0.0f);
			vbuffer.put(1.0f).put(1.0f);
			vbuffer.put(0.0f).put(1.0f);
			vbuffer.flip();

			int vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, vbuffer, GL_DYNAMIC_DRAW);

			IntBuffer ibuffer = stackMallocInt(6);
			ibuffer.put(1).put(3).put(2);
			ibuffer.put(1).put(0).put(3);
			ibuffer.flip();

			int ebo = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibuffer, GL_STATIC_DRAW);
		}

		glVertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);
		glEnableVertexAttribArray(0);

	}

	public void render(float f, float g, float h, float i, Vector3f color) {

		model.identity().translate(f, g, 0.0f).scale(h, i, 1.0f);

		shaderProgram.bind();

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);

			glUniformMatrix4fv(modelMatrixLocation, false, model.get(buf));

			glUniform3f(colorLocation, color.x, color.y, color.z);
		}

		glBindVertexArray(VAO);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);

		shaderProgram.unbind();
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}

	public void resize(int width, int height) {
		proj.identity().ortho(0, width, height, 0, -1.0f, 1.0f);

		try (MemoryStack stack = stackPush()) {
			FloatBuffer buf = stack.callocFloat(16);
			buf = proj.get(buf);

			shaderProgram.bind();
			glUniformMatrix4fv(projMatrixLocation, false, buf);
			shaderProgram.unbind();
		}

	}
}