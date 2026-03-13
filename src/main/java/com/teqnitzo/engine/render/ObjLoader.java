package com.teqnitzo.engine.render;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjLoader {

    private ObjLoader() {
    }

    public static MeshData load(String resourcePath) {
        List<Vector3f> positions = new ArrayList<>();
        List<Vector2f> texCoords = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        List<Float> vertexData = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        Map<String, Integer> vertexMap = new HashMap<>();

        try (InputStream inputStream = ObjLoader.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("OBJ resource not found: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    if (line.startsWith("v ")) {
                        String[] parts = line.split("\\s+");
                        positions.add(new Vector3f(
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        ));
                    } else if (line.startsWith("vt ")) {
                        String[] parts = line.split("\\s+");
                        texCoords.add(new Vector2f(
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2])
                        ));
                    } else if (line.startsWith("vn ")) {
                        String[] parts = line.split("\\s+");
                        normals.add(new Vector3f(
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        ));
                    } else if (line.startsWith("f ")) {
                        String[] parts = line.split("\\s+");

                        if (parts.length != 4) {
                            throw new RuntimeException("Only triangulated OBJ faces are supported: " + line);
                        }

                        for (int i = 1; i <= 3; i++) {
                            String faceToken = parts[i];
                            Integer index = vertexMap.get(faceToken);

                            if (index == null) {
                                String[] faceParts = faceToken.split("/");

                                int posIndex = Integer.parseInt(faceParts[0]) - 1;
                                int texIndex = Integer.parseInt(faceParts[1]) - 1;
                                int normIndex = Integer.parseInt(faceParts[2]) - 1;

                                Vector3f position = positions.get(posIndex);
                                Vector2f texCoord = texCoords.get(texIndex);
                                Vector3f normal = normals.get(normIndex);

                                // position
                                vertexData.add(position.x);
                                vertexData.add(position.y);
                                vertexData.add(position.z);

                                // color (white by default)
                                vertexData.add(1.0f);
                                vertexData.add(1.0f);
                                vertexData.add(1.0f);

                                // uv
                                vertexData.add(texCoord.x);
                                vertexData.add(texCoord.y);

                                // normal
                                vertexData.add(normal.x);
                                vertexData.add(normal.y);
                                vertexData.add(normal.z);

                                index = (vertexData.size() / 11) - 1;
                                vertexMap.put(faceToken, index);
                            }

                            indices.add(index);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load OBJ: " + resourcePath, e);
        }

        float[] verticesArray = new float[vertexData.size()];
        for (int i = 0; i < vertexData.size(); i++) {
            verticesArray[i] = vertexData.get(i);
        }

        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return new MeshData(verticesArray, indicesArray);
    }
}