/*
 * Copyright (c) 2021 Fraunhofer-Gesellschaft zur Foerderung der angewandten Forschung e. V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.aas4j.v3.rc02.dataformat.core.serialization;

// TODO: import org.eclipse.aas4j.v3.rc02.model.EmbeddedDataSpecification;


/**
 * Custom Serializer for class DataSpecification. Adds type information in form
 * of a reference. Uses DataSpecificationManager to resolve java type to
 * reference.
 */

// TODO: Solve the problem with EmbeddedDataSpecifications
/*public class EmbeddedDataSpecificationSerializer extends JsonSerializer<EmbeddedDataSpecification> {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedDataSpecificationSerializer.class);

    @Override
    public void serialize(EmbeddedDataSpecification data, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        if (data == null) {
            return;
        }
        Reference reference = null;
        DataSpecificationContent content = data.getDataSpecificationContent();
        if (content != null) {
            DataSpecificationInfo implicitDataSpecification = DataSpecificationManager.getDataSpecification(content.getClass());
            Reference implicitType = implicitDataSpecification != null ? implicitDataSpecification.getReference() : null;
            Reference explicitType = data.getDataSpecification();
            if (implicitType == null) {
                logger.warn(
                        "Trying to serialize unknown implementation of DataSpecificationContent ({}). "
                        + "Use DataSpecificationManager.register(Reference reference, Class<? extends DataSpecificationContent> implementation) "
                        + "to register your implementation",
                        content.getClass());
                if (explicitType == null) {
                    logger.warn("Missing type information for DataSpecificationContent! Will be serialized without type information.");
                } else {
                    reference = explicitType;
                }
            } else {
                reference = implicitType;
                if (explicitType != null && !Objects.equals(implicitType, explicitType)) {
                    logger.warn("Conflicting type information for DataSpecificationContent (implicit type: {}, explicit type: {}). Explicit type will be used.",
                            implicitType, explicitType);
                    reference = explicitType;
                }
            }
        }
        if (reference != null || content != null) {
            generator.writeStartObject();
        }
        if (reference != null) {
            generator.writeObjectField(PROP_DATA_SPECIFICATION, reference);
        }
        if (content != null) {
            generator.writeObjectField(PROP_DATA_SPECIFICATION_CONTENT, content);
        }
        if (reference != null || content != null) {
            generator.writeEndObject();
        }
    }

    @Override
    public void serializeWithType(EmbeddedDataSpecification data, JsonGenerator generator, SerializerProvider provider, TypeSerializer typedSerializer)
            throws IOException, JsonProcessingException {
        serialize(data, generator, provider);
    }

}*/