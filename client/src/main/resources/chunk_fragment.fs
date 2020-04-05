#version 330

in  vec2 outTexCoord;
in float outSimpleLight;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main()
{
    fragColor = outSimpleLight * texture(texture_sampler, outTexCoord);
    //fragColor = texture(texture_sampler, outTexCoord);
}