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
package io.adminshell.aas.v3.dataformat.aml.serialization.mappers;

import io.adminshell.aas.v3.dataformat.aml.model.caex.AttributeType;
import io.adminshell.aas.v3.dataformat.aml.serialization.AmlGenerator;
import io.adminshell.aas.v3.dataformat.aml.serialization.DefaultMapper;
import io.adminshell.aas.v3.dataformat.aml.serialization.MappingContext;
import io.adminshell.aas.v3.dataformat.core.util.AasUtils;
import io.adminshell.aas.v3.dataformat.mapping.MappingException;
import io.adminshell.aas.v3.model.Qualifier;
import io.adminshell.aas.v3.model.Reference;

public class ReferenceMapper extends DefaultMapper<Reference> {

    @Override
    protected AttributeType.Builder toAttribute(Reference value, AmlGenerator generator, MappingContext context) throws MappingException {
        AttributeType.Builder builder = AttributeType.builder()
                .withValue(AasUtils.asString(value));
        if (context.getProperty() != null) {
            builder = builder
                    .withName(getAttributeName(value, context))
                    .withRefSemantic(getRefSemantic(value, generator, context));
        }
        return builder;
    }

    @Override
    protected String getAttributeName(Reference value, MappingContext context) {
        return context.getPropertyNamingStrategy().getName(
                Reference.class,
                value,
                context.getProperty().getName());
    }
}
