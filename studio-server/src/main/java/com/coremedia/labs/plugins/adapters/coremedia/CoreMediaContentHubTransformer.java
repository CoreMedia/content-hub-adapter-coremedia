package com.coremedia.labs.plugins.adapters.coremedia;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.common.CapPropertyDescriptorType;
import com.coremedia.cap.content.Content;
import com.coremedia.contenthub.api.*;
import com.coremedia.labs.plugins.adapters.coremedia.model.CoreMediaItem;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreMediaContentHubTransformer implements ContentHubTransformer {

  private static final Logger LOG = LoggerFactory.getLogger(CoreMediaContentHubTransformer.class);

  private static final List<String> DEFAULT_IGNORED_PROPERTIES = List.of(
          "contexts",
          "ignoreUpdates",
          "linkedSettings",
          "locationTaxonomy",
          "master",
          "masterVersion",
          "resourceBundles",
          "resourceBundles2",
          "subjectTaxonomy",
          "templateSets",
          "viewtype"
  );

  private CoreMediaContentHubSettings settings;

  public CoreMediaContentHubTransformer(CoreMediaContentHubSettings settings) {
    this.settings = settings;
  }

  @Nullable
  @Override
  public ContentModel transform(Item source, ContentHubAdapter contentHubAdapter, ContentHubContext contentHubContext) throws ContentHubContentCreationException {
    if (!(source instanceof CoreMediaItem)) {
      throw new IllegalArgumentException("Cannot transform source " + source);
    }

    CoreMediaItem item = (CoreMediaItem) source;
    LOG.info("Creating content model for external content: {} (type={}).", item.getContent(), item.getCoreMediaContentType());

    String contentName = source.getName();
    ContentModel contentModel = ContentModel.createContentModel(contentName, item.getId(), item.getCoreMediaContentType());
    fillContentModel(contentModel, item.getContent());

    return contentModel;
  }

  @Nullable
  @Override
  public ContentModel resolveReference(ContentHubObject owner, ContentModelReference reference, ContentHubAdapter contentHubAdapter, ContentHubContext contentHubContext) {
    Object data = reference.getData();
    if (!(data instanceof Content)) { // this transformer only resolves content references
      throw new IllegalArgumentException("Unresolvable reference: " + reference);
    }

    Content externalContent = (Content) data;
    ContentModel referenceModel = ContentModel.createReferenceModel(externalContent.getName(), externalContent.getType().getName());
    fillContentModel(referenceModel, externalContent);

    return referenceModel;
  }

  private void fillContentModel(ContentModel contentModel, Content externalContent) {
    Map<String, Object> itemProps = externalContent.getProperties();

    itemProps.forEach((prop, value) -> {
      if (value != null && !DEFAULT_IGNORED_PROPERTIES.contains(prop)) {
        CapPropertyDescriptor propertyDescriptor = externalContent.getType().getDescriptor(prop);
        if (propertyDescriptor != null && !propertyDescriptor.getType().equals(CapPropertyDescriptorType.STRUCT)) { // STRUCTS are ignored for now
          LOG.info("Adding property '{}' (type={}).", prop, propertyDescriptor.getType());

          if (propertyDescriptor.getType().equals(CapPropertyDescriptorType.LINK)) {
            // Create references for links
            List<Content> linkedContents = externalContent.getLinks(prop);
            List<ContentModelReference> refs = new ArrayList<>();
            linkedContents.forEach(c -> {
              refs.add(ContentModelReference.create(contentModel, c.getType().getName(), c));
            });
            contentModel.put(prop, refs);

          } else {
            contentModel.put(prop, value);
          }

        }
      }
    });
  }

  private List<String> getIgnoredProperties() {
    try {
      return CollectionUtils.isNotEmpty(settings.getIgnoredProperties()) ? settings.getIgnoredProperties() : DEFAULT_IGNORED_PROPERTIES;
    } catch (Exception e) {
      return DEFAULT_IGNORED_PROPERTIES;
    }
  }

}
