package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.*;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentDao;
import by.verbitsky.servletdemo.model.dao.impl.*;
import by.verbitsky.servletdemo.model.pool.ConnectionPool;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.DaoFactory;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(AudioContentService.class)
public class AudioContentServiceTest {
    private ContentDao songDao;
    private ContentDao singerDao;
    private ContentDao reviewDao;
    private ContentDao albumDao;
    private ContentDao compilationDao;
    private ContentDao genreDao;
    private DaoFactory daoFactory;
    private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;
    private ContentService service;
    private ConnectionPool<ProxyConnection> pool;
    private ProxyConnection songConnection;
    private ProxyConnection singerConnection;
    private ProxyConnection reviewConnection;
    private ProxyConnection albumConnection;
    private ProxyConnection compilationConnection;
    private ProxyConnection genreConnection;
    private List<AudioContent> contentList;
    private User user1;

    @BeforeClass
    public void setUp() throws PoolException, ServiceException {
        daoFactory = Mockito.mock(DaoFactoryImpl.class);
        songDao = Mockito.mock(SongDaoImpl.class);
        singerDao = Mockito.mock(SingerDaoImpl.class);
        reviewDao = Mockito.mock(ReviewDaoImpl.class);
        albumDao = Mockito.mock(AlbumDaoImpl.class);
        compilationDao = Mockito.mock(CompilationDaoImpl.class);
        genreDao = Mockito.mock(GenreDaoImpl.class);

        when(daoFactory.getContentDao(ContentType.SONG)).thenReturn(songDao);
        when(daoFactory.getContentDao(ContentType.SINGER)).thenReturn(singerDao);
        when(daoFactory.getContentDao(ContentType.REVIEW)).thenReturn(reviewDao);
        when(daoFactory.getContentDao(ContentType.ALBUM)).thenReturn(albumDao);
        when(daoFactory.getContentDao(ContentType.COMPILATION)).thenReturn(compilationDao);
        when(daoFactory.getContentDao(ContentType.GENRE)).thenReturn(genreDao);

        Whitebox.setInternalState(AudioContentService.class, "daoFactory", daoFactory);

        service = AudioContentService.INSTANCE;
        pool = ConnectionPoolImpl.getInstance();
        pool.initConnectionPool();

        songConnection = pool.getConnection();
        singerConnection = pool.getConnection();
        reviewConnection = pool.getConnection();
        albumConnection = pool.getConnection();
        compilationConnection = pool.getConnection();
        genreConnection = pool.getConnection();

        song1 = new Song();
        song1.setId(1);
        song1.setSongTitle("TestSong");
        song1.setSingerId(1);
        song1.setAlbumId(1);
        song1.setGenreId(1);
        song1.setFilePath("path1");
        song1.setPrice(new BigDecimal(1));
        song1.setUploadDate(LocalDate.now());
        song2 = new Song();
        song2.setId(2);
        song2.setSongTitle("TestSong2");
        song2.setSingerId(2);
        song2.setAlbumId(2);
        song2.setGenreId(2);
        song2.setFilePath("path2");
        song2.setPrice(new BigDecimal(2));
        song2.setUploadDate(LocalDate.now());
        song3 = new Song();
        song3.setId(3);
        song3.setSongTitle("TestSong3");
        song3.setSingerId(3);
        song3.setAlbumId(3);
        song3.setGenreId(3);
        song3.setFilePath("path3");
        song3.setPrice(new BigDecimal(3));
        song3.setUploadDate(LocalDate.now());
        song4 = new Song();
        song4.setId(4);
        song4.setSongTitle("TestSong4");
        song4.setSingerId(4);
        song4.setAlbumId(4);
        song4.setGenreId(4);
        song4.setFilePath("path4");
        song4.setPrice(new BigDecimal(4));
        song4.setUploadDate(LocalDate.now());

        user1 = new User();
        user1.initBasket();
        user1.getBasket().addSong(song1);
        user1.getBasket().addSong(song2);
        user1.getBasket().addSong(song3);

        contentList = new ArrayList<>(2);
        contentList.add(song1);
        contentList.add(song2);
    }

    @AfterClass
    public void tearDown() throws PoolException {
        pool.releaseConnection(songConnection);
        pool.releaseConnection(singerConnection);
        pool.releaseConnection(reviewConnection);
        pool.releaseConnection(albumConnection);
        pool.releaseConnection(compilationConnection);
        pool.releaseConnection(genreConnection);

        pool.shutdownPool();

        songDao = null;
        singerDao = null;
        reviewDao = null;
        albumDao = null;
        compilationDao = null;
        genreDao = null;

        daoFactory = null;
        service = null;
        pool = null;
        song1 = null;
        song2 = null;
        song3 = null;
        song4 = null;

        user1 = null;
    }


    @Test
    public void testFindAllContent() throws ServiceException, DaoException {
        when(songDao.findAll()).thenReturn(contentList);
        List<AudioContent> allContent = service.findAllContent(ContentType.SONG);
        Assert.assertTrue(allContent.size() > 0);
    }

    @Test
    public void testFindContentByIdPositive() throws ServiceException, DaoException {
        when(songDao.findEntityById(2L)).thenReturn(Optional.of(song2));
        Optional<AudioContent> contentById = service.findContentById(ContentType.SONG, 2);
        Assert.assertTrue(contentById.isPresent());
    }

    @Test
    public void testFindContentByIdPositiveEmpty() throws ServiceException, DaoException {
        when(songDao.findEntityById(33L)).thenReturn(Optional.empty());
        Optional<AudioContent> contentById = service.findContentById(ContentType.SONG, 33);
        Assert.assertFalse(contentById.isPresent());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindContentByIdNegativeNullParameter() throws ServiceException {
        service.findContentById(null, 1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindContentByIdNegativeReceivedDaoException() throws ServiceException, DaoException {
        when(songDao.findEntityById(1L)).thenThrow(new DaoException("test exception"));
        service.findContentById(ContentType.SONG, 1);
    }

    @Test
    public void testCreateReviewPositive() throws DaoException, ServiceException {
        Genre genre = new Genre();
        genre.setGenreName("Test genre");
        when(genreDao.findContentByTitle("Test genre")).thenReturn(Optional.empty());
        when(genreDao.create(genre)).thenReturn(true);
        Assert.assertTrue(service.createGenre("Test genre"));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateReviewNegativeNullParameter() throws ServiceException {
        service.createGenre(null);
    }

    @Test
    public void testCreateReviewNegativeDaoFalseResult() throws ServiceException, DaoException {
        Genre genre = new Genre();
        genre.setGenreName("Test genre3");
        when(genreDao.findContentByTitle("Test genre3")).thenReturn(Optional.empty());
        when(genreDao.create(genre)).thenReturn(false);
        Assert.assertFalse(service.createGenre("Test genre3"));
    }

    @Test
    public void testCreateReviewNegativeAlreadyExist() throws ServiceException, DaoException {
        Genre genre = new Genre();
        genre.setGenreName("Test genre3");
        when(genreDao.findContentByTitle("Test genre3")).thenReturn(Optional.of(genre));
        Assert.assertFalse(service.createGenre("Test genre3"));
    }

    @Test
    public void testFindUserReviewsPositive() throws ServiceException, DaoException {
        List<AudioContent> resultList = new ArrayList<>(1);
        resultList.add(new Review());
        when(reviewDao.findContentByUser(user1)).thenReturn(resultList);
        List<AudioContent> userReviews = service.findUserReviews(user1);
        Assert.assertTrue(userReviews.size() > 0);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindUserReviewsNegativeNullParameter() throws ServiceException {
        service.findUserReviews(null);
    }

    @Test
    public void testDeleteContentByIdPositive() throws ServiceException, DaoException {
        when(songDao.delete(5L)).thenReturn(true);
        Assert.assertTrue(service.deleteContentById(ContentType.SONG, 5L));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteContentByIdNegativeNullParameter() throws ServiceException {
        service.deleteContentById(null, 5L);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteContentByIdNegativeDaoException() throws ServiceException, DaoException {
        when(songDao.delete(6L)).thenThrow(DaoException.class);
        service.deleteContentById(null, 6L);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteContentByIdNegativeDaoFalseResult() throws ServiceException, DaoException {
        when(songDao.delete(7L)).thenReturn(false);
        Assert.assertFalse(service.deleteContentById(null, 7L));
    }

    @Test
    public void testFindContentByTitlePositive() throws ServiceException, DaoException {
        when(songDao.findContentByTitle("Test title")).thenReturn(Optional.of(new Song()));
        Optional<AudioContent> test_title = service.findContentByTitle(ContentType.SONG, "Test title");
        Assert.assertTrue(test_title.isPresent());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindContentByTitleNegativeNullParameter() throws ServiceException {
        service.findContentByTitle(null, "Test title");
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindContentByTitleNegativeDaoException() throws ServiceException, DaoException {
        when(songDao.findContentByTitle("Test title2")).thenThrow(DaoException.class);
        service.findContentByTitle(ContentType.SONG, "Test title2");
    }

    @Test
    public void testFindContentByTitleNegativeEmpty() throws ServiceException, DaoException {
        when(songDao.findContentByTitle("Test title3")).thenReturn(Optional.empty());
        Optional<AudioContent> test_title3 = service.findContentByTitle(ContentType.SONG, "Test title3");
        Assert.assertFalse(test_title3.isPresent());
    }

    @Test
    public void testUpdateContentPositive() throws ServiceException, DaoException {
        when(songDao.update(song1)).thenReturn(true);
        Assert.assertTrue(service.updateContent(ContentType.SONG, song1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateContentNegativeNullParameter() throws ServiceException {
        service.updateContent(null, new Song());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateContentNegativeDaoException() throws ServiceException, DaoException {
        when(songDao.update(song2)).thenThrow(DaoException.class);
        service.updateContent(ContentType.SONG, song2);
    }

    @Test
    public void testUpdateContentNegativeDaoFalseResult() throws ServiceException, DaoException {
        Song song = new Song();
        when(songDao.update(song)).thenReturn(false);
        Assert.assertFalse(service.updateContent(ContentType.SONG, new Song()));
    }

    @Test
    public void testCreateSongPositive() throws Exception {
        when(songDao.create(song2)).thenReturn(true);
        when(singerDao.findEntityById(song2.getSingerId())).thenReturn(Optional.of(new Singer()));
        when(albumDao.findEntityById(song2.getAlbumId())).thenReturn(Optional.of(new Album()));
        when(genreDao.findEntityById(song2.getGenreId())).thenReturn(Optional.of(new Genre()));
        Assert.assertTrue(service.createSong(song2));
        when(songDao.create(song2)).thenCallRealMethod();
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateSongNegativeNullParameter() throws Exception {
        service.createSong(null);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateSongNegativeDaoException() throws Exception {
        when(songDao.create(song1)).thenThrow(DaoException.class);
        when(singerDao.findEntityById(song1.getSingerId())).thenReturn(Optional.of(new Singer()));
        when(albumDao.findEntityById(song1.getAlbumId())).thenReturn(Optional.of(new Album()));
        when(genreDao.findEntityById(song1.getGenreId())).thenReturn(Optional.of(new Genre()));
        service.createSong(song1);
        when(songDao.create(song1)).thenReturn(true);
    }

    @Test
    public void testUpdateSongPositive() throws ServiceException, DaoException {
        when(songDao.update(song3)).thenReturn(true);
        when(songDao.findEntityById(song3.getId())).thenReturn(Optional.of(new Song()));
        when(singerDao.findEntityById(song3.getSingerId())).thenReturn(Optional.of(new Singer()));
        when(albumDao.findEntityById(song3.getAlbumId())).thenReturn(Optional.of(new Album()));
        when(genreDao.findEntityById(song3.getGenreId())).thenReturn(Optional.of(new Genre()));
        Assert.assertTrue(service.updateSong(song3));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateSongNegativeNullParameter() throws ServiceException {
        service.updateSong(null);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateSongNegativeDaoException() throws Exception {
        when(songDao.update(song4)).thenThrow(DaoException.class);
        when(songDao.findEntityById(song4.getId())).thenReturn(Optional.of(new Song()));
        when(singerDao.findEntityById(song4.getSingerId())).thenReturn(Optional.of(new Singer()));
        when(albumDao.findEntityById(song4.getAlbumId())).thenReturn(Optional.of(new Album()));
        when(genreDao.findEntityById(song4.getGenreId())).thenReturn(Optional.of(new Genre()));
        Assert.assertTrue(service.updateSong(song4));
        when(songDao.update(song4)).thenReturn(true);
    }

    @Test
    public void testCreateSingerPositive() throws ServiceException, DaoException {
        String singerName = "Singer";
        Singer singer = new Singer();
        singer.setSingerName(singerName);
        when(singerDao.findContentByTitle(singerName)).thenReturn(Optional.empty());
        when(singerDao.create(singer)).thenReturn(true);
        Assert.assertTrue(service.createSinger(singerName));
    }

    @Test
    public void testCreateSingerNegativeAlreadyExist() throws ServiceException, DaoException {
        String singerName = "Singer2";
        Singer singer = new Singer();
        singer.setSingerName(singerName);
        when(singerDao.findContentByTitle(singerName)).thenReturn(Optional.of(singer));
        when(singerDao.create(singer)).thenReturn(false);
        Assert.assertFalse(service.createSinger(singerName));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateSingerNegativeDaoException() throws ServiceException, DaoException {
        String singerName = "Singer3";
        Singer singer = new Singer();
        singer.setSingerName(singerName);
        when(singerDao.findContentByTitle(singerName)).thenThrow(DaoException.class);
        Assert.assertFalse(service.createSinger(singerName));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateSingerNegativeNullParameter() throws ServiceException {
        service.createSinger(null);
    }

    @Test
    public void testCreateAlbumPositive() throws DaoException, ServiceException {
        LocalDate dateNow = LocalDate.now();
        Album album = new Album();
        album.setAlbumTitle("TestTitle1");
        album.setSingerId(1);
        album.setAlbumDate(dateNow);
        when(albumDao.create(album)).thenReturn(true);
        Assert.assertTrue(service.createAlbum("TestTitle1", 1, dateNow));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateAlbumNegativeDaoException() throws DaoException, ServiceException {
        LocalDate dateNow = LocalDate.now();
        Album album = new Album();
        album.setAlbumTitle("TestTitle2");
        album.setSingerId(2);
        album.setAlbumDate(dateNow);
        when(albumDao.create(album)).thenThrow(DaoException.class);
        Assert.assertFalse(service.createAlbum("TestTitle2", 2, dateNow));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateAlbumNegativeNullParameter() throws ServiceException {
        service.createAlbum(null, 2, null);
    }

    @Test
    public void testCreateCompilationPositive() throws DaoException, ServiceException {
        String compTitle = "Title1";
        String compType = "Type1";
        LocalDate date = LocalDate.now();
        Compilation compilation = new Compilation();
        compilation.setCompilationTitle(compTitle);
        compilation.setCompilationType(compType);
        compilation.setId(1);
        compilation.setCompilationCreationDate(date);
        when(compilationDao.create(compilation)).thenReturn(true);
        when(compilationDao.createContentDescription(compilation)).thenReturn(true);
        Assert.assertTrue(service.createCompilation(compTitle, compType, date, user1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateCompilationNegativeNullPArameter() throws ServiceException {
        Assert.assertTrue(service.createCompilation(null, null, null, null));
    }

    @Test
    public void testCreateGenrePositive() throws DaoException, ServiceException {
        String genreName = "genre1";
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        when(genreDao.create(genre)).thenReturn(true);
        when(genreDao.findContentByTitle(genreName)).thenReturn(Optional.empty());
        Assert.assertTrue(service.createGenre(genreName));
    }

    @Test
    public void testFindFilteredContentPositive() throws DaoException, ServiceException {
        SongFilter filter = new SongFilter();
        when(songDao.findFilteredContent(0, 11, filter)).thenReturn(contentList);
        List<AudioContent> filteredContent = service.findFilteredContent(filter);
        System.out.println(filteredContent.size());
        Assert.assertTrue(filteredContent.size() > 0);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindFilteredContentNegativeNullPArameter() throws ServiceException {
        service.findFilteredContent(null);
    }
}