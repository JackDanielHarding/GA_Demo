#version 330

layout (location = 0) in vec2 position;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;

void main()
{
   gl_Position = projectionMatrix * modelMatrix * vec4(position.x,position.y, 0.0f, 1.0f);
}
