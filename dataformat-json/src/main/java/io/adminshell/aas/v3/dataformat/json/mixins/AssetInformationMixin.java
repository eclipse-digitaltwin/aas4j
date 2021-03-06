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
package io.adminshell.aas.v3.dataformat.json.mixins;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.adminshell.aas.v3.model.AssetKind;

import io.adminshell.aas.v3.model.File;
import io.adminshell.aas.v3.model.Submodel;

public interface AssetInformationMixin {

    @JsonProperty("thumbnail")
    public void setDefaultThumbnail(File value);

    @JsonProperty("thumbnail")
    public File getDefaultThumbnail();

    @JsonProperty("billOfMaterial")
    public List<Submodel> getBillOfMaterials();

    @JsonProperty("billOfMaterial")
    public void setBillOfMaterials(List<Submodel> billOfMaterials);

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public AssetKind getAssetKind();
}
