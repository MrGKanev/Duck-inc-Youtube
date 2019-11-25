import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import rx.Observable;

@RunWith(AndroidJUnit4.class)
public class BackupHelperTest extends BaseAndroidTestCase {

  private File targetDir;
  private File targetAttachmentsDir;

  @Test
  public void testExportNote () throws IOException {
    Note note = new Note();
    note.setTitle("test title");
    note.setContent("test content");
    long now = Calendar.getInstance().getTimeInMillis();
    note.setCreation(now);
    note.setLastModification(now);
    BackupHelper.exportNote(targetDir, note);
    Collection<File> noteFiles = FileUtils.listFiles(targetDir, new RegexFileFilter("\\d{13}.json"),
        TrueFileFilter.INSTANCE);
    assertEquals(1, noteFiles.size());
    Note retrievedNote = rx.Observable.from(noteFiles).map(BackupHelper::importNote).toBlocking().first();
    assertTrue(note.equals(retrievedNote));
  }

  @Test
  public void testExportNoteWithAttachment () throws IOException {
    Note note = new Note();
    note.setTitle("test title");
    note.setContent("test content");
    File testAttachment = File.createTempFile("testAttachment", ".txt");
    IOUtils.write("some test content for attachment".toCharArray(), new FileOutputStream(testAttachment));
    Attachment attachment = new Attachment(Uri.fromFile(testAttachment), "attachmentName");
    note.setAttachmentsList(Collections.singletonList(attachment));

    long now = Calendar.getInstance().getTimeInMillis();
    note.setCreation(now);
    note.setLastModification(now);
    BackupHelper.exportNote(targetDir, note);
    BackupHelper.exportAttachments(null, targetAttachmentsDir,
        note.getAttachmentsList(), note.getAttachmentsListOld());
    Collection<File> files = FileUtils.listFiles(targetDir, TrueFileFilter.TRUE, TrueFileFilter.TRUE);

    assertEquals(2, files.size());
    assertTrue(note.equals(retrievedNote));
    assertEquals(retrievedAttachmentContent, FileUtils.readFileToString(new File(attachment.getUri().getPath())));
  }


  @After
  public void tearDown () throws Exception {
    FileUtils.forceDelete(targetDir);
  }
}
