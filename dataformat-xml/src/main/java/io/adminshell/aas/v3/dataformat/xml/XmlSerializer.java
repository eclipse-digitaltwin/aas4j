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
package io.adminshell.aas.v3.dataformat.xml;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import io.adminshell.aas.v3.dataformat.SerializationException;
import io.adminshell.aas.v3.dataformat.Serializer;
import io.adminshell.aas.v3.dataformat.core.ReflectionHelper;
import io.adminshell.aas.v3.dataformat.core.serialization.EnumSerializer;
import io.adminshell.aas.v3.dataformat.xml.serialization.AssetAdministrationShellEnvironmentSerializer;
import io.adminshell.aas.v3.dataformat.xml.serialization.EmbeddedDataSpecificationSerializer;
import io.adminshell.aas.v3.dataformat.xml.serialization.KeySerializer;
import io.adminshell.aas.v3.dataformat.xml.serialization.LangStringSerializer;
import io.adminshell.aas.v3.dataformat.xml.serialization.ReferenceSerializer;
import io.adminshell.aas.v3.model.AssetAdministrationShellEnvironment;
import io.adminshell.aas.v3.model.EmbeddedDataSpecification;
import io.adminshell.aas.v3.model.Key;
import io.adminshell.aas.v3.model.LangString;
import io.adminshell.aas.v3.model.Reference;

public class XmlSerializer implements Serializer {
    protected XmlMapper mapper;
    protected Map<String, String> namespacePrefixes;

    public XmlSerializer() {
        this(null);
    }

    public XmlSerializer(Map<String, String> namespacePrefixes) {
        this.namespacePrefixes = namespacePrefixes;
        buildMapper();
    }

    protected void buildMapper() {
        mapper = XmlMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .annotationIntrospector(new XmlDataformatAnnotationIntrospector())
                .defaultUseWrapper(false)
                .addModule(buildEnumModule())
                .addModule(buildCustomSerializerModule())
                .configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .build();
        ReflectionHelper.XML_MIXINS.entrySet().forEach(x -> mapper.addMixIn(x.getKey(), x.getValue()));
    }

    protected SimpleModule buildCustomSerializerModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(EmbeddedDataSpecification.class, new EmbeddedDataSpecificationSerializer());
        AssetAdministrationShellEnvironmentSerializer aasEnvSerializer;
        if (namespacePrefixes != null) {
            aasEnvSerializer = new AssetAdministrationShellEnvironmentSerializer(namespacePrefixes);
        } else {
            aasEnvSerializer = new AssetAdministrationShellEnvironmentSerializer();
        }
        module.addSerializer(AssetAdministrationShellEnvironment.class, aasEnvSerializer);
        module.addSerializer(Key.class, new KeySerializer());
        module.addSerializer(Reference.class, new ReferenceSerializer());
        module.addSerializer(LangString.class, new LangStringSerializer());
        return module;
    }

    protected SimpleModule buildEnumModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Enum.class, new EnumSerializer());
        return module;
    }

    @Override
    public String write(AssetAdministrationShellEnvironment aasEnvironment) throws SerializationException {
        try {
            ObjectWriter writer = mapper.writer();
            return writer.writeValueAsString(aasEnvironment);
        } catch (JsonProcessingException ex) {
            throw new SerializationException("serialization failed", ex);
        }
    }
}
