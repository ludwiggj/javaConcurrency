package org.ludwiggj.concurrency.model.blog;

public interface ConfirmingMicroBlogNode {
  void propagateUpdate(Update upd_, ConfirmingMicroBlogNode backup_);

  boolean tryConfirmUpdate(ConfirmingMicroBlogNode other_, Update update_);

  String getIdent();
}
