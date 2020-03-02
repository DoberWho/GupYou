package com.ctbarbanza.gupyou.mockup;

import com.ctbarbanza.gupyou.models.Commentario;
import com.ctbarbanza.gupyou.models.User;
import com.ctbarbanza.gupyou.models.Valoracion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    UserController instance;
    @Mock
    HashMap<String, User> users;
    @Mock
    HashMap<String, List<Commentario>> comentarios;
    @Mock
    HashMap<String, List<Valoracion>> valoraciones;
    @InjectMocks
    UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInit() throws Exception {
        UserController result = UserController.init();
        Assert.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme