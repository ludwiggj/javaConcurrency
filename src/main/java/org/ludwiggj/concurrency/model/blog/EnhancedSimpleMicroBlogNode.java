package org.ludwiggj.concurrency.model.blog;

public interface EnhancedSimpleMicroBlogNode {
  void propagateUpdate(Update upd_, EnhancedSimpleMicroBlogNode backup_);

  boolean confirmUpdate(EnhancedSimpleMicroBlogNode other_, Update update_, String readerId);

  String getIdent();
}
