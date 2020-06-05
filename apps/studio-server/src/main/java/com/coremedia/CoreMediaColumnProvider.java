package com.coremedia;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.Version;
import com.coremedia.contenthub.api.ContentHubObject;
import com.coremedia.contenthub.api.Folder;
import com.coremedia.contenthub.api.column.Column;
import com.coremedia.contenthub.api.column.ColumnValue;
import com.coremedia.contenthub.api.column.DefaultColumnProvider;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds a custom column for the lifecycle status of a content.
 */
class CoreMediaColumnProvider extends DefaultColumnProvider {

  @NonNull
  @Override
  public List<Column> getColumns(Folder folder) {
    List<Column> columns = new ArrayList<>(super.getColumns(folder));
    //we should set at least one column to flex, so the collection view's width will be filled.
    columns.add(new Column("created", "created", 150, -1));
    columns.add(new Column("status", "status", 100, -1));
    return columns;
  }

  @NonNull
  @Override
  public List<ColumnValue> getColumnValues(ContentHubObject hubObject) {
    CoreMediaContentHubObject coreMediaEntity = (CoreMediaContentHubObject) hubObject;
    Content content = coreMediaEntity.getContent();
    String lifecycle = getLifecycle(content);

    List<ColumnValue> columnValues = new ArrayList<>(super.getColumnValues(hubObject));
    columnValues.add(new ColumnValue("status", null, lifecycle, lifecycle));
    columnValues.add(new ColumnValue("created", content.getCreationDate(), null, null));

    return columnValues;
  }

  private String getLifecycle(Content content) {
    ContentRepository repository = content.getRepository();
    //assume we have a MLS connection
    if (repository.isMasterLiveServer() || repository.isLiveServer()) {
      return "publish";
    }

    Version checkedInVersion = content.getCheckedInVersion();
    if (checkedInVersion != null) {
      if (repository.getPublicationService().isPublished(checkedInVersion)) {
        return "publish";
      }
    }

    if (content.isCheckedOut()) {
      return "edited_by_user";
    }
    return null;
  }
}
